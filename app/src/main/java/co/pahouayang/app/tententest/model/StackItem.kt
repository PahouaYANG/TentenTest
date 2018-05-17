package co.pahouayang.app.tententest.model


data class StackItem (val instruction: Instruction, val arg : Int? = null){

    override fun toString(): String {
        var returnedString : String = "Instruction : " + instruction.name
        if(arg != null){
            returnedString += " with Arg : " + arg.toString()
        }
        return returnedString
    }
}
