import java.io.File
import java.io.FileNotFoundException

class Matrix(val rows: Int, val cols: Int, private val elements: DoubleArray) {
    init {
        require(elements.size == rows * cols) { "The number of elements does not match the number of cells in the matrix." }
    }

    fun get(row: Int, col: Int): Double {
        require(row in 0 until rows) { "Row index out of bounds." }
        require(col in 0 until cols) { "Column index out of bounds." }

        val index = row * cols + col
        return elements[index]
    }

    fun set(row: Int, col: Int, value: Double) {
        require(row in 0 until rows) { "Row index out of bounds." }
        require(col in 0 until cols) { "Column index out of bounds." }

        val index = row * cols + col
        elements[index] = value
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                sb.append(get(row, col))
                sb.append(" ")
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    companion object {
        fun fromFile(filename: String): Matrix {
            val file = File(filename)
            if (!file.exists()) throw FileNotFoundException("File not found: $file")

            val lines = file.readLines()
            val rows = lines.size
            val cols = lines.first().split(" ").size
            val elements = DoubleArray(rows * cols)

            for (row in 0 until rows) {
                val values = lines[row].split(" ")
                require(values.size == cols) { "The number of elements in a row does not match the number of columns." }

                for (col in 0 until cols) {
                    val index = row * cols + col
                    elements[index] = values[col].toDouble()
                }
            }

            return Matrix(rows, cols, elements)
        }
    }

    fun dotProduct(other: Matrix): Matrix {
        require(cols == other.rows) { "The number of columns in this matrix does not match the number of rows in the other matrix." }

        val rows = this.rows
        val cols = other.cols
        val elements = DoubleArray(rows * cols)

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                var sum = 0.0
                for (k in 0 until this.cols) {
                    sum += this.get(i, k) * other.get(k, j)
                }
                val index = i * cols + j
                elements[index] = sum
            }
        }

        return Matrix(rows, cols, elements)
    }
}

fun main(args: Array<String>) {
    if (args.size!= 2) {
        println("Pass <file1> <file2> as command line arguments")
        return
    }
    val filename1 = args[0]
    val filename2 = args[1]

    try {
        val matrixA = Matrix.fromFile(filename1)
        val matrixB = Matrix.fromFile(filename2)
        val result = matrixA.dotProduct(matrixB)
        print(result)
    } catch (e: Exception) {
        println("Error processing matrices: ${e.message}")
    }
}