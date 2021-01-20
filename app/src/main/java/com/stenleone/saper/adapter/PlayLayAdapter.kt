package com.stenleone.saper.adapter

import android.graphics.Color
import android.renderscript.Matrix2f
import android.renderscript.Matrix3f
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stenleone.saper.R
import com.stenleone.saper.activity.MainActivity
import com.stenleone.saper.entity.LayEntity
import com.stenleone.saper.interfaces.CallBackFromAdapter
import kotlinx.android.synthetic.main.cell_item_lay.view.*
import java.lang.Exception
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

        val array2: Array<IntArray> = Array(countCells / sqrt(countCells.toDouble()).toInt() + 1) { IntArray(sqrt(countCells.toDouble()).toInt()) { 0 } }

        itemsList.forEachIndexed { index, layEntity ->

            var linePosY = 0
            var localIndex = index

            while (localIndex >= array2.size) {
                linePosY += 1
                localIndex -= array2.size
            }
            if (layEntity.hasBomb) {
                array2[localIndex][linePosY] = 1
            }

        }


        var y = 0
        var x = position

        while (x >= array2.size) {
            y += 1
            x -= array2.size
        }

        try {
            if (array2[x-1][y] == 1) {
                bombFind += 1
            }
        } catch (e:Exception) {

        }
        try {
            if (array2[x+1][y] == 1) {
                bombFind += 1
            }
        } catch (e:Exception) {

        }
        try {
            if (array2[x-1][y-1] == 1) {
                bombFind += 1
            }
        } catch (e:Exception) {

        }
        try {
            if (array2[x][y-1] == 1) {
                bombFind += 1
            }
        } catch (e:Exception) {

        }
        try {
            if (array2[x+1][y-1] == 1) {
                bombFind += 1
            }
        } catch (e:Exception) {

        }
        try {
            if (array2[x-1][y+1] == 1) {
                bombFind += 1
            }
        } catch (e:Exception) {

        }
        try {
            if (array2[x][y+1] == 1) {
                bombFind += 1
            }
        } catch (e:Exception) {

        }
        try {
            if (array2[x+1][y+1] == 1) {
                bombFind += 1
            }
        } catch (e:Exception) {

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