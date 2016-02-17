package edu.etduongucsd.dopeshit;

        import android.app.SearchManager;
        import android.content.Context;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.ExpandableListView;
        import android.widget.SearchView;

        import java.util.ArrayList;

public class AllClasses extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private SearchView search;
    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    ArrayList<ClassList> classList = new ArrayList<ClassList>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_classes);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.allClassesSearch);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

        displayList();
        expandAll();

    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for(int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
    }

    private void displayList() {

        loadData();

        myList = (ExpandableListView) findViewById(R.id.allClassesExpList);
        listAdapter = new MyListAdapter(AllClasses.this, classList);
        myList.setAdapter(listAdapter);
    }

    private void loadData() {
        ArrayList<ClassInfo> listOfClasses = new ArrayList<ClassInfo>();
        ClassInfo UCSD_class = new ClassInfo("CSE 110", "Software Engineering", "Kesden");
        listOfClasses.add(UCSD_class);
        UCSD_class = new ClassInfo("CSE 105", "Automata and Computability Theory", "Shacham");
        listOfClasses.add(UCSD_class);

        ClassList UCSD_classList = new ClassList("CSE", listOfClasses);
        classList.add(UCSD_classList);



    }

    @Override
    public boolean onClose() {
        listAdapter.filterDate("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterDate(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterDate(newText);
        expandAll();
        return false;
    }
}
