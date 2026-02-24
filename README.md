# 🚀 Quantity Measurement Application

## 📏 Test-Driven Development (TDD) | OOP | Clean Code | DRY Principle

---

## 🧠 Project Overview

The **Quantity Measurement Application** is designed to validate equality, conversion, and arithmetic operations between different measurement units such as Feet, Inches, Yards, etc.

This project was implemented incrementally using:

- ✅ Test-Driven Development (TDD)
- ✅ Feature Branch Workflow
- ✅ Clean Code Practices
- ✅ DRY (Don't Repeat Yourself) Principle
- ✅ Proper Unit Conversion Strategy

---

## 📅 21 Feb 2026

### 🔹 UC10 – Generic Quantity Class with Unit Interface for Multi-Category Support

**Branch:** `feature/UC10-GenericQuantity`

### 🎯 Objective

- Create a fully generic Quantity system
- Support multiple measurement categories
- Apply interface-based design

### ✅ Implementation

- Introduced `Unit` interface
- Implemented category-specific enums (LengthUnit, WeightUnit)
- Created Generic `Quantity<T extends Unit>` class
- Ensured:
  - Type-safe unit handling
  - Category-safe operations
  - Scalable architecture

---
