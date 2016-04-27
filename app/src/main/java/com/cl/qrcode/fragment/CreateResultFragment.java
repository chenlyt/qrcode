package com.cl.qrcode.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cl.qrcode.R;
import com.cl.qrcode.activity.CreateActivity;
import com.cl.qrcode.utils.Utils;
import com.google.zxing.Result;

public class CreateResultFragment extends Fragment {


    private Bitmap result;
    private ImageView show;
    private Button test;

    public static CreateResultFragment newInstance(Bitmap bitmap) {

        Bundle args = new Bundle();
        args.putParcelable("result", bitmap);
        CreateResultFragment fragment = new CreateResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            result = arguments.getParcelable("result");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_result, container, false);
        show = (ImageView) view.findViewById(R.id.result_show);
        test = (Button) view.findViewById(R.id.test);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show.setImageBitmap(result);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result rawResult = Utils.decodeBitmap(result);
                if (rawResult != null) {
                    Utils.printResult(getActivity(), rawResult.getText());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // ((CreateActivity) getActivity()).disable();
    }
}
