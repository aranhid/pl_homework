import kotlin.math.pow
import kotlin.math.sqrt

class Point(val x: Double, val y: Double) {

    fun getDistanceTo(point: Point): Double {
        return sqrt((point.x - this.x).pow(2.0) + (point.y - this.y).pow(2.0))
    }
}