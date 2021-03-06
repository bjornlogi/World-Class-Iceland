package hbv.wci.world_class_iceland.stundatafla;

import hbv.wci.world_class_iceland.R;

import java.util.HashMap;
import java.util.List;
 
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
 
/**
 * Klasi sem utfaerir Expandable listann
 * 
 * @author Bjorn
 *
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, String> _infoChild;
 
    /**
     * Smidurinn upphafsstillir tau gagnasofn sem eru naudsynleg fyrir listann
     * 
     * @param context
     * @param listDataHeader - allir headerar, t.d. morgun siddegi o.s.frv.
     * @param listChildData - allir hoptimar sem falla undir hvern header fyrir sig
     * @param infoChild - upplysingar sem einstakur timi kann ad hafa
     */
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData, HashMap<String, String> infoChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._infoChild = infoChild;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    /**
     * Herna er loksins unnid ur ollum gognunum sem sent var inn i smidnum
     * 
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);

 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
        //convertView.setBackgroundColor(0xFF0000);
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.timiTitill);
        
        TextView info = (TextView) convertView.findViewById(R.id.timiInfo);
        
        String[] parts = new String[2];
        int dollariStadur = childText.indexOf("$");
        parts[0] = childText.substring(0, dollariStadur);
        parts[1] = childText.substring(dollariStadur+1);
        
        txtListChild.setText(parts[0]);
        info.setText(this._infoChild.get(parts[1]));

        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
            
        }
        
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        
        try{
        	if (getChildrenCount(groupPosition) > 0){
        		lblListHeader.setText(headerTitle);	 
                return convertView;
        	}
        		
        	}
        catch (Exception e){
        	
        }
        lblListHeader.setText("");
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
