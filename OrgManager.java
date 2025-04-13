package FinalProject;

import java.util.ArrayList;
import java.util.List;

public record OrgManager(OrgUnit rootUnit) {
    private record ModificationResult(OrgUnit unit, boolean modified) {}
    public record OperationResult(OrgManager manager, boolean success) {}

    // Add worker
    public static OperationResult appendWorker(OrgManager current, String targetGroup, Worker newWorker) {
        ModificationResult result = updateTree(current.rootUnit(), targetGroup, newWorker);
        return new OperationResult(
                new OrgManager(result.unit()),
                result.modified()
        );
    }

    // Remove worker
    public static OperationResult removeWorker(OrgManager current, String name) {
        ModificationResult result = removeFromTree(current.rootUnit(), name);
        return new OperationResult(
                new OrgManager(result.unit()),
                result.modified()
        );
    }

    private static ModificationResult updateTree(OrgUnit node, String targetGroup, Worker newWorker) {
        if (node instanceof Worker) {
            return new ModificationResult(node, false);
        }

        Group group = (Group) node;
        boolean modified = false;

        // Check if current group is the target
        if (group.groupName().equals(targetGroup)) {
            List<OrgUnit> newUnits = new ArrayList<>(group.orgUnits());
            newUnits.add(newWorker);
            return new ModificationResult(
                    new Group(group.groupName(), group.boss(), newUnits),
                    true
            );
        }

        // Process children
        List<OrgUnit> newChildren = new ArrayList<>();
        for (OrgUnit child : group.orgUnits()) {
            ModificationResult childResult = updateTree(child, targetGroup, newWorker);
            newChildren.add(childResult.unit());
            modified |= childResult.modified();
        }

        return new ModificationResult(
                new Group(group.groupName(), group.boss(), newChildren),
                modified
        );
    }

    private static ModificationResult removeFromTree(OrgUnit node, String name) {
        if (node instanceof Worker worker) {
            boolean removed = worker.name().equals(name);
            return new ModificationResult(removed ? null : worker, removed);
        }

        Group group = (Group) node;
        boolean modified = false;
        List<OrgUnit> newChildren = new ArrayList<>();

        for (OrgUnit child : group.orgUnits()) {
            ModificationResult childResult = removeFromTree(child, name);
            if (childResult.unit() != null) {
                newChildren.add(childResult.unit());
            }
            modified |= childResult.modified();
        }

        return new ModificationResult(
                new Group(group.groupName(), group.boss(), newChildren),
                modified
        );
    }
    public void displayOrg() {
        if (rootUnit != null) {
            System.out.println(rootUnit.toString(0) + "\n");
        }
    }
}
