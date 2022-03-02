package com.moov.moovapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.moov.moovapp.R
import com.moov.moovapp.databinding.FragmentUserListBinding
import com.moov.moovapp.databinding.UserListContentBinding
import com.moov.moovapp.model.User
import com.moov.moovapp.viewmodel.UserViewModel

/**
 * A Fragment representing a list of Users. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of users, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * user details. On larger screens, the Navigation controller presents the list of users and
 * user details side-by-side using two vertical panes.
 */

class UserListFragment : Fragment() {

    private val model: UserViewModel by activityViewModels()

    private var _binding: FragmentUserListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.user_detail_nav_container)

        model.getUsers().observe(viewLifecycleOwner) {
            recyclerView?.adapter = UserRecyclerViewAdapter(
                it, itemDetailFragmentContainer
            )
        }

    }

    class UserRecyclerViewAdapter(
        private val values: List<User>,
        private val itemDetailFragmentContainer: View?
    ) :
        RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val binding =
                UserListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.id.toString()
            holder.contentView.text = item.firsName

            with(holder.itemView) {
                tag = item
                setOnClickListener { itemView ->
                    if (itemDetailFragmentContainer != null) {
                        itemDetailFragmentContainer.findNavController()
                            .navigate(R.id.fragment_user_detail)
                    } else {
                        itemView.findNavController().navigate(R.id.show_user_detail)
                    }
                }
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(binding: UserListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val idView: TextView = binding.idText
            val contentView: TextView = binding.content
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}