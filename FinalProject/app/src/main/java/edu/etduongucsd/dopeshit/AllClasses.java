package edu.etduongucsd.dopeshit;

        import android.app.SearchManager;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ExpandableListView;
        import android.widget.SearchView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
//List of departments with courses underneath
public class AllClasses extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private SearchView search;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private List<Department> depart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depart = HomeScreen.depart;
        setContentView(R.layout.activity_all_classes);

        /* Find the toolbar by id, and set it as the action bar. Whenever the 'Note' is clicked,
         * it will return to the home screen.
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(AllClasses.this, HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        toolbar.findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllClasses.this, SettingsPage.class));
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.allClassesSearch);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

        TextView allClass = (TextView) findViewById(R.id.allCoursesTitle);
        Typeface myType = Typeface.createFromAsset(getAssets(), "Lob.otf");
        allClass.setTypeface(myType);

        displayList();


    }

    private void displayList() {

        loadData();

        myList = (ExpandableListView) findViewById(R.id.allClassesExpList);
        listAdapter = new ExpandableListAdapter(AllClasses.this, listDataHeader, listDataChild);
        myList.setAdapter(listAdapter);
        resetDepartments();

        // Listview Group click listener
        myList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        myList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        myList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // Listview on child click listener
        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                retrieveSelected(listDataHeader.get(groupPosition), listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                startActivity(new Intent(AllClasses.this, SelectedCourse.class));

                return false;
            }
        });
    }

    //Reset departments on list
    private void resetDepartments () {
        depart = new ArrayList<Department>();
        for (Department tmpDept : HomeScreen.depart) {
            depart.add(tmpDept);
        }
    }

    //Set correct list of departments
    private void setDepartments (List<String> d) {

        depart = new ArrayList<Department>();
        for (Department tmpDept : HomeScreen.depart) {
            for (String s : d) {
                if (tmpDept.name.equals(s)) {
                    depart.add(tmpDept);
                }
            }
        }
    }
    //Retrieve selected departments and courses from list
    private void retrieveSelected(String dept, String course){
        for(Department tempDept : depart){
            if(tempDept.name.equals(dept)){
                HomeScreen.selectedDepart = tempDept;
                for(Course tempCourse : tempDept.courses){
                    if(tempCourse.name.equals(course)){
                        HomeScreen.selectedCourse = tempCourse;

                    }
                }
            }
        }


    }
    //Load the data for the list
    private void loadData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        for(Department department : depart){
            listDataHeader.add(department.toString());
            List<String> courseName = new ArrayList<String>();
            for(Course course : department.courses){
                courseName.add(course.toString());
            }
            listDataChild.put(listDataHeader.get(listDataHeader.size() - 1), courseName);
        }
    }

    @Override
    public boolean onClose() {
        listAdapter.filterDate("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterDate(query);
        setDepartments(listAdapter._listDataHeader);
        loadData();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterDate(newText);
        setDepartments (listAdapter._listDataHeader);
        loadData();
        return false;
    }
}
