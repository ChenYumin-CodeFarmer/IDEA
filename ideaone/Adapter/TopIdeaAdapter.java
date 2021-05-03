package com.example.ideaone.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ideaone.IdeaActivity;
import com.example.ideaone.R;
import com.example.ideaone.topIdea;

import java.util.List;

public class TopIdeaAdapter extends RecyclerView.Adapter<TopIdeaAdapter.ViewHolder> {

    private List<topIdea> mIdeaList;

    static class ViewHolder extends RecyclerView.ViewHolder{     //定义一个内部类继承自RecyclerView.ViewHolder
        TextView ideaContext;
        TextView ideaTitle;
        View ideaView;
        TextView ideaTopId;
        TextView ideaHot;

        public ViewHolder(View view){   //在这个构造函数中传入一个view，一般为子项的最外层布局
            super(view);
            ideaView = view;            //用于保存子项最外层布局的实例
            ideaContext = (TextView) view.findViewById(R.id.context_text_top);
            ideaTitle = (TextView) view.findViewById(R.id.title_text_top);
            ideaTopId = (TextView) view.findViewById(R.id.top_id);
            ideaHot = (TextView) view.findViewById(R.id.hot_text_top);
        }
    }

    public TopIdeaAdapter(List<topIdea> ideaList){
        mIdeaList = ideaList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){   //将子项布局加载进来并创建实例
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_idea_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ideaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                topIdea Idea = mIdeaList.get(position);
                Intent intent = new Intent(parent.getContext(), IdeaActivity.class);
                intent.putExtra(IdeaActivity.IDEA_NAME,Idea.getIdeatitle());
                intent.putExtra(IdeaActivity.IDEA_CONTENT,Idea.getIdeacontext());
                intent.putExtra(IdeaActivity.IDEA_ADDRESS,Idea.getIdeaaddress());
                intent.putExtra(IdeaActivity.IDEA_CONTACT,Idea.getIdeacontact());
                intent.putExtra(IdeaActivity.IDEA_UID,Idea.getIdeauid());
                intent.putExtra(IdeaActivity.IDEA_ICON_ID,R.drawable.idea_bg);
                parent.getContext().startActivity(intent);
            }
        });
        holder.ideaContext.setOnClickListener(new View.OnClickListener() {      //设置监听器
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                topIdea Idea = mIdeaList.get(position);
                Toast.makeText(v.getContext(),"you click context"+Idea.getIdeacontext(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder,int position){       //对进入屏幕内的子项进行赋值
        topIdea midea = mIdeaList.get(position);
        holder.ideaContext.setText(midea.getIdeacontext());
        holder.ideaTitle.setText(midea.getIdeatitle());
        holder.ideaTopId.setText(midea.getTopId());
        holder.ideaHot.setText(midea.getLike());
    }

    @Override
    public int getItemCount(){                      //告诉RecyclerView子项的个数
        return mIdeaList.size();
    }
}

