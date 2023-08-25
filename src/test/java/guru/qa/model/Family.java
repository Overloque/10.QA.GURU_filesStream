package guru.qa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Family {
    //    {
//        "parent": true,
//            "children": [
//        {
//            "name": "Maria",
//                "age": 18
//        },
//        {
//            "name": "Mike",
//                "age": 19
//        }
//  ]
//    }
    @JsonProperty("parent")
    private boolean parent;
    @JsonProperty("children")
    private List<FamilyChildren> children;

    public List<FamilyChildren> getFamilyChildren() {
        return children;
    }

    public boolean isParent() {
        return parent;
    }

    public static class FamilyChildren {
        @JsonProperty("name")
        private String name;
        @JsonProperty("age")
        private Integer age;

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }
    }
}
