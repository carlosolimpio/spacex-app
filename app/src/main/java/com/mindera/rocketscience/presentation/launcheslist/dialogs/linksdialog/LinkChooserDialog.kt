package com.mindera.rocketscience.presentation.launcheslist.dialogs.linksdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.DialogFragment
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.LayoutLinkItemBinding
import com.mindera.rocketscience.domain.common.capitalize

class LinkChooserDialog(private val links: List<String>) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mappedLinks = mapItems()
        val dialog = AlertDialog.Builder(context)
        if (mappedLinks.isNotEmpty()) {
            val adapter = MyCustomAdapter(mappedLinks)
            dialog.setTitle(getString(R.string.click_to_open_one_of_the_links_bellow))
                .setAdapter(adapter) { _, which ->
                    openLink((adapter.getItem(which) as LinkItem).url)
                }
        } else {
            dialog.setTitle(getString(R.string.no_links_found))
                .setMessage(getString(R.string.unfortunately_no_links_attached))
                .setIcon(R.drawable.icon_failed)
                .setNeutralButton(getString(R.string.close)) { _, _ -> dismiss() }
        }
        return dialog.create()
    }

    private fun mapItems(): List<LinkItem> {
        val newList = links.filter { it.isNotBlank() }

        return newList.toSet().map { url ->
            val linkType = when {
                url.contains(YOUTUBE_PREFIX) -> LinkType.YOUTUBE
                url.contains(WIKIPEDIA_PREFIX) -> LinkType.WIKIPEDIA
                else -> LinkType.ARTICLE
            }

            val iconResId = when (linkType) {
                LinkType.YOUTUBE -> R.drawable.icon_video
                LinkType.WIKIPEDIA -> R.drawable.icon_wiki
                else -> R.drawable.icon_article
            }

            LinkItem(linkType, url, iconResId)
        }
    }

    private fun openLink(url: String) {
        val uri = Uri.parse(url)
        Intent(Intent.ACTION_VIEW, uri).also {
            startActivity(it)
        }
    }

    inner class MyCustomAdapter(
        private val allItems: List<LinkItem>
    ) : BaseAdapter() {
        override fun getCount(): Int = allItems.size
        override fun getItem(p0: Int): Any = allItems[p0]
        override fun getItemId(p0: Int) = p0.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding = LayoutLinkItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            binding.apply {
                textLink.text = allItems[position].type.name.capitalize()
                iconLink.setImageResource(allItems[position].icon)
            }

            return binding.root
        }
    }

    companion object {
        const val TAG = "FilterDialog"
        private const val YOUTUBE_PREFIX = "youtu"
        private const val WIKIPEDIA_PREFIX = "wikipedia"
    }

    data class LinkItem(
        val type: LinkType,
        val url: String,
        val icon: Int
    )

    enum class LinkType {
        YOUTUBE,
        ARTICLE,
        WIKIPEDIA
    }
}
