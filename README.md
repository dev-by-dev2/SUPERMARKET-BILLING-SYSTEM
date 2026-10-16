# Supermarket Billing System

A modular, terminal-based supermarket billing application developed in Java. The project simulates a real-world retail checkout process, combining foundational Data Structures (resizable arrays, direct indexing, record encapsulation) with Object-Oriented Design Patterns (Strategy Pattern for discounts).

---

## Features

- **Categorized Inventory Catalog**: Products are structured across 13 supermarket categories (Dairy, Bakery, Snacks, Personal Care, etc.).
- **Direct-Index Selection**: Fast item lookup and terminal navigation using 0-based index mapping.
- **Dynamic Cart Management**: Stateful shopping cart utilizing dynamic arrays (`ArrayList`) to hold multiple item quantities.
- **Polymorphic Discount Strategies**: Clean implementation of the Strategy Pattern supporting:
  - `PercentageDiscount`: Deducts a percentage from the total line item price.
  - `FixedDiscount`: Deducts a flat amount per unit purchased.
  - `NoDiscount`: Default fallback for standard-priced items.
- **Automated Bill Generation**: Tabular terminal invoice displaying line items, quantities, applied discounts, and optional carry-bag charges.
- **Persistent Storage**: Exports the finalized invoice directly to `bill.txt` using buffered file streams.

---

## Data Structures & Algorithms (DSA) Concepts

- **Dynamic Array (`java.util.ArrayList`)**: Manages the catalog, category items, and transactional cart records with $O(1)$ amortized insertion and $O(1)$ indexed access.
- **Direct Index Mapping**: Converts user menu inputs directly into array indices for constant-time $O(1)$ retrieval.
- **Linear Accumulation**: Single-pass $O(m)$ iteration over cart records to compute sub-totals, cumulative discounts, and grand totals.
- **Dynamic Character Buffer (`StringBuilder`)**: Prevents $O(n^2)$ copying overhead from repeated immutable string concatenations during receipt generation.

---

## Project Structure

```text
├── Main.java         # Application entry point, console menus, and input loops
├── Bill.java         # Invoice string formatting, totals calculation, and file I/O
├── Cart.java         # Shopping cart state container and CartItem inner class
├── Category.java     # Category entity storing product lists
├── Item.java         # Product entity (name, unit price, and discount strategy)
├── Discount.java     # Abstract Discount base class & concrete strategy subclasses
└── bill.txt          # Generated receipt file (created after checkout)
