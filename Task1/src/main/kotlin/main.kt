fun main(args: Array<String>) {
    val meter = DistanceMeter()
    meter
        .add(Point(0.0, 0.0))
        .add(Point(2.0, 0.0))
        .add(Point(2.0, 3.0))
    println(meter.measure())
}