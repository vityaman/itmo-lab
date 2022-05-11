package ru.vityaman.tidb.collection.data;

public interface Entry {
    String key();
    TicketEntry ticket();

    final class Simple implements Entry {
        private final String key;
        private final TicketEntry ticket;

        public Simple(String key, TicketEntry ticket) {
            this.key = key;
            this.ticket = ticket;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public TicketEntry ticket() {
            return ticket;
        }

        @Override
        public String toString() {
            return String.format(
                "\"%s\"': %s",
                key, ticket
            );
        }
    }
}
