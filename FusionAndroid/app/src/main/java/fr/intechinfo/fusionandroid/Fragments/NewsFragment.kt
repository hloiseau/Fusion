package fr.intechinfo.fusionandroid.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.intechinfo.fusionandroid.R


class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }
}// Required empty public constructor
