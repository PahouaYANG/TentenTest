package co.pahouayang.app.tententest.model


import android.util.Log
import co.pahouayang.app.tententest.model.Instruction.*
import java.lang.ref.WeakReference

/**
 * TODO : add all messages in string.xml /!\ need context
 */
class Computer (stackLineNumber : Int) {
    
    private val TAG = "ComputerTag"

    private var stack : Array<StackItem?>
    private var stackAddress : Int = 0
    private var showStackInfo : Boolean = false

    init {
        stack = arrayOfNulls<StackItem>(stackLineNumber)
        showStackInfo = false
    }

    fun insert(stackItem: StackItem) : Computer{
        stack.set(stackAddress, stackItem)
        stackAddress++
        return this
    }

    fun setAddress(address: Int) : Computer{
        stackAddress = address
        return this
    }

    fun execute() : Computer{
        //showAllStack(stackAddress)
        //Log.d(TAG,"\n/*******************************/\n")

        runStack(stackAddress)

        //Log.d(TAG,"\n/*******************************/\n")
        //showAllStack(stackAddress)

        return this
    }

    fun showStackInfo(isShown : Boolean){
        showStackInfo = isShown
    }


    //region private methods
    private fun runStack(currentStackAddress : Int?, savedStackAddress : Int? = currentStackAddress){
        //error condition
        if(currentStackAddress == null
                || (currentStackAddress != null && currentStackAddress > stack.size)
                || (savedStackAddress != null && savedStackAddress > stack.size)){
            getListener()?.onErrorInstruction("address null")
            return
        }

        //go out condition
        if(currentStackAddress == stack.size){
            return
        }

        //timer to see the execution
        //Thread.sleep(500)

        //show execution in log
        Log.d(TAG,  "Position : " + currentStackAddress + " "+ stack[currentStackAddress].toString())

        //show execution if asked
        if(showStackInfo) {
            getListener()?.onInformationMessage("Position : " + currentStackAddress + " - " + stack[currentStackAddress].toString())
        }

        //run instructions
        when(stack[currentStackAddress]?.instruction){
            MULT -> {
                multiplyInstruction(currentStackAddress, savedStackAddress)
            }
            CALL -> {
                //savedAddress is current+1 in order to came back directly on the next instruction
                runStack(stack[currentStackAddress]!!.arg, currentStackAddress)
                return
            }
            RET -> {
                returnInstruction(currentStackAddress,savedStackAddress)
                return
            }
            STOP -> {
                return
            }
            PRINT -> {
                printInstruction(currentStackAddress)
            }
            PUSH -> {
                //do nothing as we just push the value
            }
        }

        runStack(currentStackAddress+1, savedStackAddress)

    }

    private fun showAllStack(stackAddress : Int){
        if(stackAddress == stack.size){
            return
        }
        if(stack[stackAddress] != null) {
            Log.d(TAG,  "Position : " + stackAddress + " "+ stack[stackAddress].toString())
        }else{
            Log.d(TAG,"Position : " + stackAddress.toString())
        }
        showAllStack(stackAddress+1)
    }

    private fun multiplyInstruction(currentStackAddress : Int, savedStackAddress : Int? = currentStackAddress){
        //limit condition
        if(savedStackAddress == null){
            getListener()?.onErrorInstruction("address null")
            return
        }
        //checking condition according to data
        if(stack[savedStackAddress-1]!= null
                && stack[savedStackAddress-1]?.instruction == PUSH
                && stack[savedStackAddress-1]?.arg != null
                && stack[savedStackAddress-2]!= null
                && stack[savedStackAddress-2]?.instruction == PUSH
                && stack[savedStackAddress-2]?.arg != null){
            //mutl
            val multValue = stack[savedStackAddress-1]!!.arg!! * stack[savedStackAddress-2]!!.arg!!
            //save result in current address
            stack[currentStackAddress] = StackItem(PUSH, multValue)
            Log.d(TAG,  "Position : " + currentStackAddress + " "+ stack[currentStackAddress].toString())
            //pop values
            stack[savedStackAddress-1] = null
            stack[savedStackAddress-2] = null
        }else {
            getListener()?.onFailedInstruction("fail mult because previous stacked values don't exist")
        }
    }

    private fun printInstruction(currentStackAddress : Int){
        //checking condition
        if(stack[currentStackAddress-1]?.instruction == PUSH && stack[currentStackAddress-1]?.arg != null){
            //print
            getListener()?.onSuccessInstruction(stack[currentStackAddress-1]!!.arg.toString())
            //pop value
            stack[currentStackAddress] = null
        }else{
            getListener()?.onFailedInstruction("fail print because previous stacked value doesn't exist")
        }

    }

    private fun returnInstruction(currentStackAddress : Int, savedStackAddress : Int? = currentStackAddress){
        //limit condition
        if(savedStackAddress == null || currentStackAddress == savedStackAddress+1){
            getListener()?.onErrorInstruction("fail ret because returned address doesn't exist")
            return
        }
        //pop stack
        stack[currentStackAddress] = null
        //return to previous address +1 to be on the next stack position
        runStack(savedStackAddress + 1)
    }
    //endregion

    //region listener
    private var mComputerListener: WeakReference<ComputerListener>? = null

    private fun getListener(): ComputerListener? {
        return if (mComputerListener != null) mComputerListener!!.get() else null
    }

    fun setListener(computerListener: ComputerListener) {
        mComputerListener = WeakReference(computerListener)
    }

    interface ComputerListener {
        fun onErrorInstruction(errorMessage : String)
        fun onFailedInstruction(failedMessage : String)
        fun onSuccessInstruction(successMessage : String)
        fun onInformationMessage(infoMessage : String)

    }
    //endregion

}