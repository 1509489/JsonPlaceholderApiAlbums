package com.pixelart.jsonplaceholderapi_albums.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pixelart.jsonplaceholderapi_albums.R
import kotlinx.android.synthetic.main.message_fragment.view.*


class MessageFragment: BottomSheetDialogFragment(), View.OnClickListener{

    private var listener: OnInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.message_fragment, container, false)

        listener?.setMessage(rootView.tvMessage)
        rootView.ibClose.setOnClickListener(this)
        
        return rootView
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ibClose->{
                dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    interface OnInteractionListener{
        fun setMessage(tvMessage: TextView)
    }
}