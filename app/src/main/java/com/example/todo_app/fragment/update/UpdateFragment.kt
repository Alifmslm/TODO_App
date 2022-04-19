package com.example.todo_app.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo_app.R
import com.example.todo_app.data.model.NoteData
import com.example.todo_app.data.viewModelsdata.NotesViewModel
import com.example.todo_app.databinding.FragmentUpdateBinding
import com.example.todo_app.fragment.SharedVieModel


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModels : SharedVieModel by viewModels()
    private val mNotesViewModel : NotesViewModel by viewModels()

    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        binding.spUpdate.onItemSelectedListener = mSharedViewModels.listener

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmitemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmitemRemoval() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete '${args.currentItem.title}' ? ")
            .setMessage("Are you sure you want to remove '${args.currentItem.title}'?")
            .setPositiveButton("Yes") {_, _ ->
                mNotesViewModel.delateData(args.currentItem)
                Toast.makeText(requireContext(), "Berhasil Dihapus : ${args.currentItem.title}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            .setNegativeButton("No"){_,_ ->}
            .create()
            .show()
    }

    private fun updateItem() {
        val title = binding.etUptitle.text.toString()
        val description = binding.etDescupdate.text.toString()
        val getPriority = binding.spUpdate.selectedItem.toString()

        val validation = mSharedViewModels.verifyDataFromUser(title, description)
        if (validation) {
            val updateItem = NoteData(
                args.currentItem.id,
                title,
                mSharedViewModels.parsePriority(getPriority),
                description
            )
            mNotesViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Berhasil di update", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Tolong isi", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}