package com.github.freitzzz.kitkatgpt.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.freitzzz.kitkatgpt.R
import com.github.freitzzz.kitkatgpt.viewmodel.ConversationViewModel

class BottomNaturalLanguageInput : Fragment() {
    private lateinit var viewModel: ConversationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_natural_language_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.viewModel = ViewModelProvider(requireActivity())[ConversationViewModel::class.java]

        val input = view.findViewById<EditText>(R.id.fragment_bottom_natural_language_input_edit_text)
        val send = view.findViewById<View>(R.id.fragment_bottom_natural_language_input_icon_send)
        input.setOnEditorActionListener { _,_,_ -> onEditorSubmit(input) }
        send.setOnClickListener { _ -> onEditorSubmit(input) }
    }

    private fun onEditorSubmit(
        input: EditText,
    ): Boolean {
        if(input.text.isEmpty()) {
            return true
        }

        if (viewModel.isOngoing()) {
            viewModel.update(input.text.toString())
        } else {
            viewModel.start(input.text.toString())
        }

        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(input.windowToken, 0)
        input.text.clear()

        return false
    }
}