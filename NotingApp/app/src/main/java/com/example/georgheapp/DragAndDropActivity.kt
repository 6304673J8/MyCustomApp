package com.example.georgheapp

import android.os.Bundle
import android.view.DragEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.georgheapp.databinding.ActivityDragAndDropBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.reflect.safeCast

class DragAndDropActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDragAndDropBinding

    private val chipsLabels = listOf(
        "Blue supergiant", "Sun-Like Star", "Red Dwarf", "Brown Dwarf", "Red Giant",// 1st phase
        "Supernova", "Blackhole", "Neutron Star", "White Dwarf", // 2nd phase
        "Neutrino", "Chupa-chups", // Not star life
    )

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityDragAndDropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chipsLabels.forEach {
            val chip = Chip(this)
            chip.text = it

            //binding.chipGroup1.addView(chip)
            chip.setOnLongClickListener{
                val shadow = View.DragShadowBuilder(chip)
                chip.startDragAndDrop(null, shadow, chip, 0)
                true
            }
            binding.chipGroup1.addView(chip)
        }
        binding.chipGroup2.setOnDragListener(dragListener)
    }

    private val dragListener = View.OnDragListener { objectiveView, event ->
//normal cast        val movingChip = event.localState as Chip
        val movingChip = Chip::class.safeCast(event.localState) ?: return@OnDragListener false

        when(event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                movingChip.setTextColor(getColor(R.color.transparent))
                movingChip.setChipStrokeColorResource(R.color.black)
                movingChip.chipStrokeWidth = 4f
                //movingChip.setBackgroundColor(getColor(R.color.transparent))
            }

            DragEvent.ACTION_DROP -> {
                //binding.chipGroup1.removeView(movingChip)
                //binding.chipGroup2.addView(movingChip)

                val parent = ChipGroup::class.safeCast(movingChip.parent)
                parent?.removeView(movingChip)

                ChipGroup::class.safeCast(objectiveView)?.addView(movingChip)
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                movingChip.setTextColor(getColor(R.color.black))
                movingChip.chipStrokeWidth = 0f
            }
        }
        true
    }
}