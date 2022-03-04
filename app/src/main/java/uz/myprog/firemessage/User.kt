package uz.myprog.firemessage

data class User(
    val message:String? = null,
    val sender:Boolean,
    val receiver:Boolean
)