package shop.model;

import java.util.ArrayList;
import java.util.List;

/** Pesanan yang dibuat menggunakan Builder. */
public class Order {
    private final String orderId;
    private final Address address;
    private final List<Line> lines;
    private final String note;
    private final boolean giftWrap;
    private final boolean express;

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.address = builder.address;
        this.lines = List.copyOf(builder.lines);
        this.note = builder.note;
        this.giftWrap = builder.giftWrap;
        this.express = builder.express;
    }

    public String getOrderId() {
        return orderId;
    }

    public Address getAddress() {
        return address;
    }

    public List<Line> getLines() {
        return lines;
    }

    public String getNote() {
        return note;
    }

    public boolean isGiftWrap() {
        return giftWrap;
    }

    public boolean isExpress() {
        return express;
    }

    /** Builder untuk membuat pesanan. */
    public static class Builder {
        private final String orderId;
        private final Address address;
        private final List<Line> lines = new ArrayList<>();
        private String note;
        private boolean giftWrap;
        private boolean express;

        public Builder(String orderId, Address address) {
            this.orderId = orderId;
            this.address = address;
        }

        public Builder addLine(Product product, int quantity) {
            lines.add(new Line(product, quantity));
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder giftWrap(boolean giftWrap) {
            this.giftWrap = giftWrap;
            return this;
        }

        public Builder express(boolean express) {
            this.express = express;
            return this;
        }

        public Order build() {
            if (lines.isEmpty()) {
                throw new IllegalStateException("Pesanan harus memiliki minimal satu item");
            }
            return new Order(this);
        }
    }

    /** Satu baris pesanan: produk dan jumlahnya. */
    public record Line(Product product, int quantity) {
    }
}
