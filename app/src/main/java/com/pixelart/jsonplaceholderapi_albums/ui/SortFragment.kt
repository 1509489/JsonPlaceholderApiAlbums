package com.pixelart.jsonplaceholderapi_albums.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pixelart.jsonplaceholderapi_albums.R
import kotlinx.android.synthetic.main.sort_fragment.view.*


class SortFragment: BottomSheetDialogFragment(), View.OnClickListener{

    private var listener: OnInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.sort_fragment, container, false)

        rootView.btnSortA_Z.setOnClickListener(this)
        rootView.btnSortZ_A.setOnClickListener(this)
        
        return rootView
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSortA_Z ->{
                listener?.onSort(true)
                dismiss()
            }
            R.id.btnSortZ_A ->{
                listener?.onSort(false)
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
        fun onSort(sortAtoZ: Boolean)
    }
}