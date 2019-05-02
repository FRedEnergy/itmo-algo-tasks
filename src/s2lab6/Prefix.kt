package s2lab6

import java.io.File

object Prefix {

    @JvmStatic
    fun main(args: Array<String>) {
        val scan = File("prefix.in").bufferedReader()
        val s = scan.readLine()

        with(File("prefix.out").printWriter()) {
            print(s.prefix().joinToString(" "))
            close()
        }

    }

    fun String.prefix(): IntArray {
        val p = IntArray(length)
        (1 until length).forEach {
            var k = p[it - 1]
            while (k > 0 && get(it) != get(k))
                k = p[k - 1]
            if (get(it) == get(k))
                k++
            p[it] = k
        }
        return p
    }
}