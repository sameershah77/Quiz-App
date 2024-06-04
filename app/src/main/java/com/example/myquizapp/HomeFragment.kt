package com.example.myquizapp


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class HomeFragment : androidx.fragment.app.Fragment() {
    var flag = true
    val arr = ArrayList<Subject>()
    lateinit var adapter: recylerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val home_name = view.findViewById<TextView>(R.id.home_name)

        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue<User>()
                    home_name.setText(user!!.name.toString())
                }
                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }
            }
        )

        val home_score = view.findViewById<TextView>(R.id.home_score)
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid).child("Scores").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Handle data change
                if (dataSnapshot.exists()) {
                    val arr = ArrayList<Int>()
                    for (scoreSnapshot in dataSnapshot.children) {
                        val score = scoreSnapshot.getValue(Int::class.java)?.toInt()
                        if (score != null) {
                            arr.add(score)
                        }
                    }
                    home_score.text = "${arr.sum()}"

                } else {
                    // No scores found
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
        makeArr()
        //Recycler View Settings
        adapter = recylerViewAdapter(requireContext(),arr)
        val recylerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = recylerViewAdapter(requireContext(),arr)
        recylerView.layoutManager = GridLayoutManager(requireContext(),2)
        val switch_layout = view.findViewById<ImageView>(R.id.switch_layout)

        switch_layout.setOnClickListener {
            if(flag == true) {
                switch_layout.setImageResource(R.drawable.grid)
                recylerView.layoutManager = LinearLayoutManager(requireContext())
                flag = false
            }
            else {
                switch_layout.setImageResource(R.drawable.linear)
                recylerView.layoutManager = GridLayoutManager(requireContext(),2)
                flag = true
            }
        }
        recylerView.adapter = adapter

        return view
    }

    fun makeArr() {
        val arr1 = ArrayList<QuestionModel>()

        arr1.add(QuestionModel("If a triangle has angles measuring 30°, 60°, and 90°, what is the ratio of the length of the side opposite the 60° angle to the hypotenuse?","12","15","12","52","102"))
        arr1.add(QuestionModel("What is the value of π (pi) to two decimal places?","3.14","3.14","3.13","6.28","3.18"))
        arr1.add(QuestionModel("sIf a box contains 5 red balls, 4 green balls, and 3 blue balls, what is the probability of randomly selecting a green ball?","2/9","1/4","1/8","4","2/9"))
        arr1.add(QuestionModel("What is the sum of the interior angles of a hexagon?","540°","540°","180°","360°","270°"))
        arr1.add(QuestionModel("If a rectangular prism has dimensions of length = 4 cm, width = 3 cm, and height = 5 cm, what is its volume?","60 cm³","90 cm³","60 cm³","40 cm³","30 cm³"))
        arr1.add(QuestionModel("What is the area of a right-angled triangle with base 8 units and height 15 units?","60 square units","60 square units","90 square units","30 square units","120 square units"))
        arr1.add(QuestionModel(" What is the square of 6?","36","36","81","49","30°"))

        arr.add(Subject(R.drawable.maths,"Maths",arr1));


        val arr2 = ArrayList<QuestionModel>()
        arr2.add(QuestionModel("#include <iostream>\n" +
                "\n" +
                "int main() {\n" +
                "    int x = 5;\n" +
                "    int y = x++ + ++x;\n" +
                "\n" +
                "    std::cout << \"y = \" << y << std::endl;\n" +
                "\n" +
                "    return 0;\n" +
                "}","y = 12","y = 11","y = 12","y = 13","y = 16"))
        arr2.add(QuestionModel("#include <iostream>\n" +
                "#include <vector>\n" +
                "\n" +
                "int main() {\n" +
                "    std::vector<int> numbers = {1, 2, 3, 4, 5};\n" +
                "    std::cout << numbers[5] << std::endl;\n" +
                "\n" +
                "    return 0;\n" +
                "}","Undefined behavior (Accessing out-of-bounds)","0","Undefined behavior (Accessing out-of-bounds)","1","2"))

        arr2.add(QuestionModel("#include <iostream>\n" +
                "#include <unordered_map>\n" +
                "\n" +
                "int main() {\n" +
                "    std::unordered_map<std::string, int> myMap;\n" +
                "    myMap[\"apple\"] = 3;\n" +
                "    myMap[\"orange\"] = 5;\n" +
                "    std::cout << myMap[\"banana\"] << std::endl;\n" +
                "\n" +
                "    return 0;\n" +
                "}","0","1"," Undefined behavior (Key not found)","compiler error","0"))

        arr2.add(QuestionModel("#include <iostream>\n" +
                "#include <set>\n" +
                "\n" +
                "int main() {\n" +
                "    std::set<int> mySet = {3, 1, 4, 1, 5, 9};\n" +
                "    std::cout << mySet.size() << std::endl;\n" +
                "\n" +
                "    return 0;\n" +
                "}","5","5","1","4","8"))
        arr2.add(QuestionModel("#include <iostream>\n" +
                "#include <queue>\n" +
                "\n" +
                "int main() {\n" +
                "    std::queue<int> myQueue;\n" +
                "    myQueue.push(10);\n" +
                "    myQueue.push(20);\n" +
                "    myQueue.push(30);\n" +
                "    std::cout << myQueue.pop() << std::endl;\n" +
                "\n" +
                "    return 0;\n" +
                "}","Compiler error","10","20","30","Compiler error"))

        arr.add(Subject(R.drawable.data_structure,"Data Structure",arr2))


        val arr3 = ArrayList<QuestionModel>()
        arr3.add(QuestionModel("import java.util.ArrayList;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        ArrayList<Integer> numbers = new ArrayList<>();\n" +
                "        numbers.add(1);\n" +
                "        numbers.add(2);\n" +
                "        numbers.add(3);\n" +
                "        numbers.add(4);\n" +
                "        numbers.add(5);\n" +
                "        System.out.println(numbers.get(5));\n" +
                "    }\n" +
                "}\n","IndexOutOfBoundsException","2","IndexOutOfBoundsException","1","0"))
        arr3.add(QuestionModel("import java.util.HashMap;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        HashMap<String, Integer> myMap = new HashMap<>();\n" +
                "        myMap.put(\"apple\", 3);\n" +
                "        myMap.put(\"orange\", 5);\n" +
                "        System.out.println(myMap.get(\"banana\"));\n" +
                "    }\n" +
                "}\n","Null","2","31","Null","Error"))
        arr3.add(QuestionModel("import java.util.HashSet;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        HashSet<Integer> mySet = new HashSet<>();\n" +
                "        mySet.add(3);\n" +
                "        mySet.add(1);\n" +
                "        mySet.add(4);\n" +
                "        mySet.add(1);\n" +
                "        mySet.add(5);\n" +
                "        mySet.add(9);\n" +
                "        System.out.println(mySet.size());\n" +
                "    }\n" +
                "}\n","5","2","5","7","9"))


        arr.add(Subject(R.drawable.java,"Java",arr3))

        val arr4 = ArrayList<QuestionModel>()
        arr4.add(QuestionModel("#include <iostream>\n" +
                "#include <vector>\n" +
                "\n" +
                "int main() {\n" +
                "    std::vector<int> numbers = {1, 2, 3, 4, 5};\n" +
                "    std::cout << numbers[5] << std::endl;\n" +
                "    return 0;\n" +
                "}\n","Undefined behavior (Accessing out-of-bounds)","0","1","2","Undefined behavior (Accessing out-of-bounds)"))
        arr4.add(QuestionModel("#include <iostream>\n" +
                "#include <unordered_map>\n" +
                "\n" +
                "int main() {\n" +
                "    std::unordered_map<std::string, int> myMap;\n" +
                "    myMap[\"apple\"] = 3;\n" +
                "    myMap[\"orange\"] = 5;\n" +
                "    std::cout << myMap[\"banana\"] << std::endl;\n" +
                "    return 0;\n" +
                "}\n","Undefined behavior (Key not found)","1","0","Undefined behavior (Key not found)","Error"))
        arr.add(Subject(R.drawable.c,"C++ Basics",arr4))


        val arr5 = ArrayList<QuestionModel>()
        arr5.add(QuestionModel("Android is -","an operating system","a web server","a web browser","an operating system","None of above"))
        arr5.add(QuestionModel("What is an Intent in Android development?","It is a mechanism to pass data between activities or start new activities."," It is a data structure used for storing application preferences.","It is a mechanism to pass data between activities or start new activities.","It is a layout file defining the UI of an Android application.","It is a layout file defining the UI of an Android application."))
        arr.add(Subject(R.drawable.android,"Android Development",arr5))


        val arr6 = ArrayList<QuestionModel>()
        arr6.add(QuestionModel("What is the purpose of a file system in an operating system?","1","22","31","4","4"))
        arr6.add(QuestionModel("2+2 = ?","To organize and store data on storage devices.","To execute programs and run applications.","To organize and store data on storage devices.","To provide a user interface for interacting with the computer.","To manage the computer's hardware resources."))

        arr.add(Subject(R.drawable.os,"Operating System",arr6))


        val arr7 = ArrayList<QuestionModel>()
        arr7.add(QuestionModel("What do you mean by one to many relationships?","One teacher can have many classes","dont know","Many classes may have many teachers","One teacher can have many classes","Many classes may have many teachers"))
        arr7.add(QuestionModel("A Database Management System is a type of _________software.","It is a type of system software","it is a kind of general software","It is a type of system software","It is a kind of application software","A & C"))
        arr.add(Subject(R.drawable.database,"DBMS",arr7))

        val arr8 = ArrayList<QuestionModel>()
        arr8.add(QuestionModel("There are ___ levels of heading in HTML","6","5","4","8","6"))
        arr8.add(QuestionModel("We can test our web pages through:","Using Dreamweaver","Using Photoshop","Using Photoshop","Using Dreamweaver","Using Photoshop"))
        arr.add(Subject(R.drawable.web_development,"Web Development",arr8))

        val arr9 = ArrayList<QuestionModel>()
        arr9.add(QuestionModel("Which type of JavaScript language is ___","Object-Based","Object-Based","Object-Oriented","High-level","High-level"))
        arr9.add(QuestionModel("varx=5,y=1  \n" +
                "var obj ={ x:10}  \n" +
                "with(obj)  \n" +
                "{  \n" +
                "      alert(y)  \n" +
                "}  ","1","Error","2","1","10"))
        arr.add(Subject(R.drawable.js,"Javascript",arr9))
//
        val arr10 = ArrayList<QuestionModel>()
        arr10.add(QuestionModel("CSS stands for -","Cascading style sheets","Color and style sheets","Cascading style sheets,","Cascade style sheets","None of the above"))
        arr10.add(QuestionModel("The property in CSS used to change the background color of an element is -","background-color","bg","color","All","background-color"))
        arr.add(Subject(R.drawable.css,"CSS",arr10))
//
        val arr11 = ArrayList<QuestionModel>()
        arr11.add(QuestionModel("n a flowchart, a calculation (process) is represented by _____?","Rectangle","A diamond","circle","Rectangle","square"))
        arr11.add(QuestionModel("If ....... then ....... else ....... End If check ____?","Two conditions","Only one condition","Two conditions","Three conditions","Multiple conditions"))
        arr.add(Subject(R.drawable.algorithm,"Algorithm",arr11))


        val arr12 = ArrayList<QuestionModel>()
        arr12.add(QuestionModel("Which among the following temperature scale is based upon absolute zero?","Kelvin","Kelvin","Rankine","Celsius","Fahrenheit"))
        arr12.add(QuestionModel("The minimum speed required to put a satellite into a given orbit around earth is known as:","Orbital velocity","Escape velocity","Orbital velocity","Kinetic velocity","None of the above"))
        arr.add(Subject(R.drawable.physics,"Physics",arr12))

    }
}