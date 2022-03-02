package com.moov.moovapp.view

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.moov.moovapp.databinding.FragmentUserDetailBinding

/**
 * A fragment representing a single User detail screen.
 * This fragment is either contained in a [UserListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class UserDetailFragment : Fragment() {

    /**
     * The placeholder content this fragment is presenting.
     */
    private var user: PlaceholderContent.PlaceholderItem? = null

    private var itemDetailTextView: TextView? = null
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentUserDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    private val dragListener = View.OnDragListener { v, event ->
        if (event.action == DragEvent.ACTION_DROP) {
            val clipDataItem: ClipData.Item = event.clipData.getItemAt(0)
            val dragData = clipDataItem.text
            user = PlaceholderContent.ITEM_MAP[dragData]
            updateContent()
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_USER_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                user = PlaceholderContent.ITEM_MAP[it.getString(ARG_USER_ID)]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        val rootView = binding?.root

        toolbarLayout = binding?.toolbarLayout
        itemDetailTextView = binding?.userDetail

        updateContent()
        rootView?.setOnDragListener(dragListener)

        return rootView
    }

    private fun updateContent() {
        toolbarLayout?.title = user?.content

        // Show the placeholder content as text in a TextView.
        user?.let {
            itemDetailTextView?.text = it.details
        }
    }

    companion object {
        /**
         * The fragment argument representing the user ID that this fragment
         * represents.
         */
        const val ARG_USER_ID = "user_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}