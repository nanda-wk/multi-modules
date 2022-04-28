package com.teamyear.common.entity;

public enum OrderStatus {

    PENDING{
        @Override
        public String description() {
            return "Order is being processed.";
        }
    },

    SHIPPING{
        @Override
        public  String description() {
            return "Deliverer is delivering the package";
        }
    },

    DELIVERED{
        @Override
        public  String description() {
            return "Customer received products";
        }
    },

    CANCELLED{
        @Override
        public String description() {
            return "Order was rejected";
        }
    };

    public abstract String description();
}