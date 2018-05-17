package co.pahouayang.app.tententest


class MainContract {

    interface MainView{

        fun showInformationMessage(infoMessage : String)
        fun showSuccessInstruction(successMessage : String)
        fun showFailedInstruction(failedMessage : String)
        fun showErrorInstruction(errorMessage : String)

    }

    interface MainPresenterInt{

        fun setComputerInstructions(showStackInfo : Boolean)

    }
}