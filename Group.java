package FinalProject;

import java.util.ArrayList;
import java.util.List;

public record Group(String groupName, Worker boss, List<OrgUnit> orgUnits) implements OrgUnit {
    public Group {
        orgUnits = List.copyOf(orgUnits);
    }

    @Override
    public String toString(int i) {
        String indent = "  ".repeat(i);
        String header = "\n" + indent + "Group: " + groupName + ", boss's name: " + boss.name();
        String children = orgUnitsToString(i + 1);
        return header + (children.isEmpty() ? "" : "\n" + children);
    }

    public String getName() {
        return groupName;
    }

    public List<OrgUnit> getOrgUnits() {
        return orgUnits;
    }

    private String orgUnitsToString(int i) {
        List<String> childStrings = new ArrayList<>();
        for (OrgUnit unit : orgUnits) {
            if (unit != null) {
                childStrings.add(unit.toString(i));
            }
        }
        return String.join("\n", childStrings);
    }
}
