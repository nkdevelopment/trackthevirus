package development.nk.trackthevirus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import development.nk.trackthevirus.entity.Asthenis;

/**
 * Created by NKdevelopment on 30/4/2020.
 */
public class AdapterAsthenis extends RecyclerView.Adapter<AdapterAsthenis.MyViewHolder> implements Filterable {

    final private ListItemClickListener mOnClickListener;

    private List<Asthenis> list;
    private List<Asthenis> listFiltered = new ArrayList<>();

    public int getCount() {

        return listFiltered.size();
    }


    public Asthenis getItem(int position) {

        return listFiltered.get(position);
    }

    public long getItemId(int position) {

        return listFiltered.get(position).hashCode();
    }

    public void resetData() {

        listFiltered = list;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFiltered = list;
                } else {
                    List<Asthenis> filteredList = new ArrayList<>();
                    for (Asthenis row : list) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getSurname().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    listFiltered = filteredList;
                }

                filterResults.values = listFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFiltered = (ArrayList<Asthenis>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    /**
     * Cache of the children views for a list item.
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // each data item is just a string in this case
        private TextView vat;
        private TextView date;
        private TextView name;
        private TextView surname;
        private TextView age;
        private TextView town;
        private TextView region;
        private TextView mobile;
        private TextView phone_home;


        public MyViewHolder(View itemView) {
            super(itemView);
            vat = itemView.findViewById(R.id.row_vat);
            date = itemView.findViewById(R.id.row_date);
            name = itemView.findViewById(R.id.row_name);
            surname = itemView.findViewById(R.id.row_surname);
            age = itemView.findViewById(R.id.row_age);
            town = itemView.findViewById(R.id.row_town);
            region = itemView.findViewById(R.id.row_region);
            mobile = itemView.findViewById(R.id.row_mobile);
            phone_home = itemView.findViewById(R.id.row_phone_home);

            // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            itemView.setOnClickListener(this);
        }

        void bind(Asthenis asthenis) {

            vat.setText("VAT: "+Long.toString(asthenis.getVat()));

            String dateString = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY).format(new Date(asthenis.getDate_long()));
            date.setText(dateString);

            name.setText(asthenis.getName());
            surname.setText(asthenis.getSurname());
            age.setText(asthenis.getAge());
            town.setText(asthenis.getTown());
            region.setText(asthenis.getRegion());
            mobile.setText(asthenis.getMobile());
            phone_home.setText(asthenis.getPhone_home());
//            Picasso.with(itemView.getContext()).load(pelates.getImageUrl()).into(image);
        }

        public void onClick (View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }


    /**
     * Constructor for PelatesAdapter that accepts a list and the specification for
     * the ListItemClickListener and ListItemLongClickListener
     * @param list list of Asthenis
     * @param mOnClickListener clickListener
     */
    public AdapterAsthenis(List<Asthenis> list, ListItemClickListener mOnClickListener) {
        this.list = list;
        this.listFiltered = list;
        this.mOnClickListener = mOnClickListener;
    }


    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolder will be created to fill the screen and allow scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     *                 can use this viewType integer to provide a different layout. See
     *                 {@link androidx.recyclerview.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                 for more details.
     * @return A new ProductViewHolder that holds the View for each list item
     */
    @Override
    public AdapterAsthenis.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_asthenis_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder  The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(listFiltered.get(position));
//        holder.itemView.setTag(list.get(position));

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available
     */
    @Override
    public int getItemCount() {
        return listFiltered.size();
    }


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
