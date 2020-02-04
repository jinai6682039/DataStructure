package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FriendScan {

    static int[][] friends = {{1, 5}, {3, 5}, {4, 5}, {1, 4}, {5, 6}, {8, 1}, {9, 20}, {98, 11},
            {13, 76}, {98, 77}, {2, 1}};

    public static class Group {
        private List<Integer> members;

        Group() {
            members = new LinkedList<>();
        }

        void addMember(int member) {

            if (members == null) {
                members = new LinkedList<>();
            }

            if (members.contains(member)) {
                return;
            }

            members.add(member);
        }

        void addMember(int[] members) {

            for (int member : members) {
                addMember(member);
            }
        }

        boolean inGroup(int member) {

            if (members == null) {
                return false;
            }

            return members.contains(member);
        }

        void reGroup(Group group) {

            if (group == null || group.members == null || group.members.size() == 0) {
                return;
            }

            if (members == null) {
                members = new LinkedList<>();
            }

            members.addAll(group.members);
        }
    }

    public static Group findInGroup(List<Group> groups, int member) {
        if (groups == null || groups.size() == 0) {
            return null;
        }

        for (Group group : groups) {
            if (group.inGroup(member)) {
                return group;
            }
        }

        return null;
    }

    public static void buildFriemds(int[][] friends) {

        List<Group> groups = new ArrayList<>();

        for (int[] friend : friends) {

            if (friend == null || friend.length != 2) {
                continue;
            }

            int member1 = friend[0];
            int member2 = friend[1];

            Group group1 = findInGroup(groups, member1);
            Group group2 = findInGroup(groups, member2);

            if (group1 == null && group2 == null) {
                Group group = new Group();
                group.addMember(friend);
                groups.add(group);
            } else if (group1 == null || group2 == null) {
                Group group = group1 == null ? group2 : group1;
                group.addMember(friend);
            } else {
                if (group1 != group2) {
                    groups.remove(group2);
                    group1.reGroup(group2);
                }
            }
        }

        for (Group group : groups) {
            StringBuilder sb = new StringBuilder();
            sb.append("group: ");
            for (int member : group.members) {
                sb.append(member).append(", ");
            }
            System.out.println(sb.toString());
        }
    }

    public final static void main(String[] args) {
        buildFriemds(friends);
    }
}
