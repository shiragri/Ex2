Ex2-  Spreadsheet System (Java)
Overview
This project implements a simple spreadsheet system in Java, supporting numeric, text, and formula cells.
It allows evaluating formulas, detecting circular dependencies, and saving/loading data from a file.
Features
	• Cell Types: Numeric, text, and formula cells.
	• Formula Evaluation: Basic arithmetic operations (+, -, *, /).
	• Circular Dependency Detection: Handles circular references in formulas.
	• File I/O: Save and load the spreadsheet in CSV format.
Usage
	• Set cell values using set(x, y, value).
	• Evaluate formulas automatically with eval().
	• Save spreadsheet to a file with save("filename.csv").
	• Load a spreadsheet from a file with load("filename.csv").

