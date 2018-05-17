package co.pahouayang.app.tententest

import co.pahouayang.app.tententest.model.Computer
import co.pahouayang.app.tententest.model.Instruction.*
import co.pahouayang.app.tententest.model.StackItem

class MainPresenter(var mainView: MainContract.MainView)
    : MainContract.MainPresenterInt, Computer.ComputerListener {

    companion object {
        private val PRINT_TENTEN_BEGIN = 50;
        private val MAIN_BEGIN = 0;
    }


    //region implemented contract
    override fun setComputerInstructions(showStackInfo : Boolean) {

        // Create new computer with a stack of 100 addresses
        val computer = Computer(100)

        // Set Listener to get replied message
        computer.setListener(this)

        // Set show stack info
        computer.showStackInfo(showStackInfo)

        // Instructions for the print_tenten function
        computer.setAddress(PRINT_TENTEN_BEGIN)
                .insert(StackItem(MULT))
                .insert(StackItem(PRINT))
                .insert(StackItem(RET))

        // The start of the main function
        computer.setAddress(MAIN_BEGIN)
                .insert(StackItem(PUSH, 1009))
                .insert(StackItem(PRINT))

        // Return address for when print_tenten function finishes
        computer.insert(StackItem(PUSH, 6))

        // Setup arguments and call print_tenten
        computer.insert(StackItem(PUSH, 101))
                .insert(StackItem(PUSH, 10))
                .insert(StackItem(CALL, PRINT_TENTEN_BEGIN))

        // Stop the program
        computer.insert(StackItem(STOP))

        // Launch the program
        computer.setAddress(MAIN_BEGIN)
                .execute()

    }
    //endregion

    //region implemented listener
    override fun onInformationMessage(infoMessage: String) {
        mainView.showInformationMessage(infoMessage)
    }

    override fun onSuccessInstruction(successMessage: String) {
        mainView.showSuccessInstruction(successMessage)
    }

    override fun onFailedInstruction(failedMessage: String) {
        mainView.showFailedInstruction(failedMessage)
    }

    override fun onErrorInstruction(errorMessage: String) {
        mainView.showErrorInstruction(errorMessage)
    }
    //endregion

}

