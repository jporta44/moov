package com.moov.moovapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.moov.moovapp.R
import com.moov.moovapp.databinding.FragmentUserListBinding
import com.moov.moovapp.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A Fragment representing a list of Users. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of users, which when touched,
 * lead to a [UserDetailFragment] representing
 * user details. On larger screens, the Navigation controller presents the list of users and
 * user details side-by-side using two vertical panes.
 */

class UserListFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentUserListBinding? = null
    private var adapter = UserAdapter(UserAdapter.UserComparator)

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView? = binding?.userList
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.user_detail_nav_container)
        adapter.onItemClick = { user ->
            // Mark user as selected in ViewModel
            userViewModel.select(user)
            // Handle Phone or Tablet navigation
            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_user_detail)
            } else {
                binding?.root?.findNavController()?.navigate(R.id.show_user_detail)
            }
        }

        recyclerView?.adapter = adapter

        // Feed adapter with flow of User PagingData
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}