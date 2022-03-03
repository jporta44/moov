package com.moov.moovapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.moov.moovapp.databinding.FragmentUserDetailBinding
import com.moov.moovapp.model.User
import com.moov.moovapp.viewmodel.UserViewModel

/**
 * A fragment representing a single User detail screen.
 * This fragment is either contained in a [UserListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class UserDetailFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()

    private var itemDetailTextView: TextView? = null
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentUserDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        val rootView = binding?.root

        toolbarLayout = binding?.toolbarLayout
        itemDetailTextView = binding?.userDetail

        userViewModel.selected.observe(viewLifecycleOwner) {
            updateContent(it)
        }


        return rootView
    }

    private fun updateContent(user: User) {
        toolbarLayout?.title = user.getFullName()

        user.let {
            itemDetailTextView?.text = it.email
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}