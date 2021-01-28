interface Rule {
    val errorMessage: String
    fun validate(password: String): Boolean
}