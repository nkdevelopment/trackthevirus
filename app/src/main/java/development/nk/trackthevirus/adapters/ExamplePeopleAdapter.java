package development.nk.trackthevirus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import development.nk.trackthevirus.R;
import development.nk.trackthevirus.entity.Asthenis;

/**
 * Created by NKdevelopment on 5/5/2020.
 */
//public class ExamplePeopleAdapter extends FirestoreRecyclerOptions<Asthenis> {

//    private RecycleItemClick recycleItemClick;
//    private static final String TAG = "PeopleListAdapter";
//
//    public ExamplePeopleAdapter(FirebaseRecyclerOptions<User> options) {
//        super(options, true);
//    }
//
//    public interface RecycleItemClick {
//        void onItemClick(String userId, User user, int position);
//    }
//
//    public void setRecycleItemClick(RecycleItemClick recycleItemClick) {
//        this.recycleItemClick = recycleItemClick;
//    }
//
//    @Override
//    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.layout_item_people, parent, false);
//        return new PeopleViewHolder(view);
//    }
//
//    @Override
//    protected void onBindViewHolder(PeopleViewHolder holder, int position, User model) {
//        holder.bind(model);
//    }
//
//    @Override
//    protected void onChildUpdate(User model,
//                                 ChangeEventType type,
//                                 DataSnapshot snapshot,
//                                 int newIndex,
//                                 int oldIndex) {
//        model.setUserId(snapshot.getKey());
//        super.onChildUpdate(model, type, snapshot, newIndex, oldIndex);
//    }
//
//    @Override
//    protected boolean filterCondition(User model, String filterPattern) {
//        return model.getFirstName().toLowerCase().contains(filterPattern) ||
//                model.getLastName().toLowerCase().contains(filterPattern);
//    }
//
//    public class PeopleViewHolder extends RecyclerView.ViewHolder {
//
//        LayoutItemPeopleBinding mBinding;
//
//        PeopleViewHolder(View view) {
//            super(view);
//            mBinding = DataBindingUtil.bind(view);
//        }
//
//        public void bind(User user) {
//            Picasso.with(mBinding.peopleImage.getContext())
//                    .load(user.getImage())
//                    .placeholder(R.drawable.place_holder_user)
//                    .into(mBinding.peopleImage);
//            mBinding.peopleName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    User user = getItem(pos);
//                    recycleItemClick.onItemClick(user.getUserId(), user, pos);
//                }
//            });
//        }
//    }
//}
