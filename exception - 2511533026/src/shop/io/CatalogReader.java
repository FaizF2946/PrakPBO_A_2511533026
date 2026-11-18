package shop.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import shop.exception.CatalogLoadException;
import shop.model.PhysicalProduct;
import shop.model.Product;

public class CatalogReader {
    private int skippedCount;

    public List<Product> load(Path path) throws CatalogLoadException {
        List<Product> products = new ArrayList<>();
        skippedCount = 0;

        if (path == null) {
            throw new CatalogLoadException("Path katalog tidak boleh null");
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    skippedCount++;
                    continue;
                }

                String[] parts = trimmed.split(";");
                if (parts.length < 4) {
                    skippedCount++;
                    continue;
                }

                String sku = parts[0].trim();
                String name = parts[1].trim();
                String rawPrice = parts[2].trim();
                String rawWeight = parts[3].trim();

                try {
                    long price = parsePrice(rawPrice);
                    int weight = Integer.parseInt(rawWeight);
                    if (sku.isBlank() || name.isBlank() || price <= 0 || weight <= 0) {
                        throw new IllegalArgumentException("invalid row");
                    }
                    products.add(new PhysicalProduct(sku, name, price, weight));
                } catch (RuntimeException e) {
                    skippedCount++;
                }
            }
            return products;
        } catch (IOException e) {
            throw new CatalogLoadException("Gagal membuka katalog " + path, e);
        }
    }

    public int getSkippedCount() {
        return skippedCount;
    }

    private long parsePrice(String raw) {
        String value = raw.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("price empty");
        }
        value = value.replace("Rp", "").replace(" ", "");
        value = value.replace(".", "").replace(",", "");
        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException("price not numeric");
        }
        return Long.parseLong(value);
    }
}
