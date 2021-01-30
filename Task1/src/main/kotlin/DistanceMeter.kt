import kotlin.math.pow
import kotlin.math.sqrt

class DistanceMeter() {
    private val points = arrayListOf<Point>()

    fun add(point: Point): DistanceMeter {
        points.add(point)
        return this
    }

    fun measure(): Double {
        var distance = 0.0
        for (i in points.indices) {
            if (i != points.size - 1) {
                distance += points[i].getDistanceTo(points[i+1])
            }
        }
        return distance
    }
}