package com.stenleone.saper.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stenleone.saper.R
import com.stenleone.saper.activity.MainActivity
import com.stenleone.saper.entity.LayEntity
import com.stenleone.saper.interfaces.CallBackFromAdapter
import kotlinx.android.synthetic.main.cell_item_lay.view.*
import java.lang.IndexOutOfBoundsException
import java.util.ArrayList
import kotlin.math.sqrt
import kotlin.random.Random

class PlayLayAdapter(val countCells: Int, private val callBack: CallBackFromAdapter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemsList: ArrayList<LayEntity> = arrayListOf()
    private val bombPosition: ArrayList<Int> = arrayListOf()
    private val flagPosition: ArrayList<Int> = arrayListOf()
    private val nonActivePosition: ArrayList<Int> = arrayListOf()

    init {
        for (i in 1..countCells) {
            itemsList.add(LayEntity())
        }

        for (i in 1..sqrt(countCells.toDouble()).toInt()) {
            while (true) {
                val randomPosition = Random.nextInt(0, itemsList.count())

                if (!itemsList[randomPosition].hasBomb) {
                    itemsList[randomPosition].hasBomb = true
                    bombPosition.add(randomPosition)
                    break
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CellsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cell_item_lay, parent, false).rootView
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CellsHolder).bind()
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    inner class CellsHolder(private val cellItemView: View) :
        RecyclerView.ViewHolder(cellItemView) {

        fun bind() {

            setupClick()
        }

        private fun setupClick() {

            cellItemView.setOnClickListener {
                if (!nonActivePosition.contains(adapterPosition)) {
                    if (MainActivity.isHelmet) {

                        if (itemsList[adapterPosition].hasBomb && !flagPosition.contains(adapterPosition)
                        ) {
                            cellItemView.itemImage.setBackgroundResource(R.drawable.bomb)
                            callBack.clickOnBomb()
                        } else if (!itemsList[adapterPosition].hasBomb && !flagPosition.contains(adapterPosition)
                        ) {
                            cellItemView.text.text = findBomb(adapterPosition).toString()
                            nonActivePosition.add(adapterPosition)
                        }
                    } else {
                        if (flagPosition.contains(adapterPosition)) {
                            cellItemView.itemImage.setBackgroundColor(Color.TRANSPARENT)
                            flagPosition.remove(adapterPosition)
                        } else {
                            cellItemView.itemImage.setBackgroundResource(R.drawable.flag)
                            flagPosition.add(adapterPosition)
                            checkWin()
                        }
                    }
                }
            }
        }
    }

    private fun findBomb(position: Int): Int {

        var bombFind = 0
        val spanCount = sqrt(countCells.toDouble()).toInt()

        val listLists = arrayListOf<ArrayList<ArrayList<LayEntity>>>()
        val localItemList = itemsList

        for (i in 1..spanCount) {

            val lineList = ArrayList<LayEntity>()

            for (ii in 1..spanCount) {
                lineList.add(localItemList[ii])
                localItemList.removeAt(ii)
            }
            
            listLists.add(arrayListOf(lineList))
        }

        return bombFind
    }

    fun checkWin() {
        bombPosition.sort()
        flagPosition.sort()

        if (bombPosition == flagPosition) {
            callBack.win()
        }
    }

}