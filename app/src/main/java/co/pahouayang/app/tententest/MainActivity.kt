package co.pahouayang.app.tententest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import co.pahouayang.app.tententest.MainContract.MainView
import co.pahouayang.app.tententest.adapter.StackLineAdapter
import co.pahouayang.app.tententest.model.MessageType.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , MainView{

    private var presenter: MainPresenter? = null
    private var recyclerViewAdapter= StackLineAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init presenter
        presenter = MainPresenter(this)

        //init recycler
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.setNestedScrollingEnabled(false)
        rv_main.adapter = recyclerViewAdapter

        //init buttons
        cb_main.setText(R.string.main_checkbox_text)
        btn_main.setText(R.string.main_button_text)
        btn_main.setOnClickListener() {
            //clear recycler of stack
            recyclerViewAdapter.clearMessages()
            //init computer and set its instructions
            presenter?.setComputerInstructions(cb_main.isChecked) }
    }

    //region implemented contract
    override fun showInformationMessage(infoMessage: String) {
        runOnUiThread { recyclerViewAdapter.addMessage(infoMessage, INFO) }
    }
    override fun showSuccessInstruction(successMessage: String) {
        //TODO : check why doesn't appear step by step even on UiThread
        runOnUiThread { recyclerViewAdapter.addMessage(successMessage, SUCCESS) }
    }

    override fun showFailedInstruction(failedMessage: String) {
        recyclerViewAdapter.addMessage(failedMessage, FAILURE)
    }

    override fun showErrorInstruction(errorMessage: String) {
        recyclerViewAdapter.addMessage(errorMessage,ERROR)
    }
    //endregion
}
