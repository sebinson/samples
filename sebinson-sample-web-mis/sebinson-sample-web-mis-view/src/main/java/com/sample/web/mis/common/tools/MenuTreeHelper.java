package com.sample.web.mis.common.tools;

import java.util.ArrayList;
import java.util.List;

import com.sample.web.common.beans.TreeNode;
import com.sample.web.mis.persistence.domain.SampleMenu;
import com.sample.web.mis.persistence.domain.SampleMenuOperation;

public class MenuTreeHelper {

    List<SampleMenu> rootMenus;
    List<SampleMenu> childMenus;
    List<SampleMenuOperation> childOpers;

    public MenuTreeHelper() {
    }

    public MenuTreeHelper(List<SampleMenu> rootMenus, List<SampleMenu> childMenus) {
        this.rootMenus = rootMenus;
        this.childMenus = childMenus;
    }

    public MenuTreeHelper(List<SampleMenu> rootMenus, List<SampleMenu> childMenus, List<SampleMenuOperation> childOpers) {
        this.rootMenus = rootMenus;
        this.childMenus = childMenus;
        this.childOpers = childOpers;
    }

    public List<TreeNode> getTreeNode() {
        return getRootNodes();
    }

    private List<TreeNode> getRootNodes() {
        List<TreeNode> rootNodes = new ArrayList<TreeNode>();
        for (SampleMenu menu : rootMenus) {
            TreeNode node = MenuToNode(menu);
            if (node != null) {
                addChlidNodes(node);
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    private void addChlidNodes(TreeNode rootNode) {
        List<TreeNode> childNodes = new ArrayList<TreeNode>();
        for (SampleMenu menu : childMenus) {
            if (rootNode.getDataId().equals(menu.getMenuParentId())) {
                TreeNode node = MenuToNode(menu);
                if (childOpers != null && !childOpers.isEmpty()) {// childOpers或空或有值，只能这两种情况
                                                                  // ，若其它情况不做处理
                    addChlidOper(node);
                }
                childNodes.add(node);
            }
        }
        rootNode.setChildren(childNodes);
    }

    private void addChlidOper(TreeNode treeNode) {
        List<TreeNode> childNodes = new ArrayList<TreeNode>();
        for (SampleMenuOperation oper : childOpers) {
            if (treeNode.getDataId().equals(oper.getMenuId())) {
                TreeNode node = OperToNode(oper);
                childNodes.add(node);
            }
        }
        treeNode.setChildren(childNodes);
    }

    private TreeNode MenuToNode(SampleMenu menu) {
        if (menu == null) {
            return null;
        }
        TreeNode node = new TreeNode();
        node.setId(menu.getMenuId());
        node.setDataId(menu.getMenuId());
        node.setText(menu.getMenuName());
        node.setUrl(menu.getMenuUri());
        node.setParentId(menu.getMenuParentId());
        node.getAttributes().put("type", "0");
        node.getAttributes().put("id", menu.getMenuId());
        return node;
    }

    private TreeNode OperToNode(SampleMenuOperation oper) {
        if (oper == null) {
            return null;
        }
        TreeNode node = new TreeNode();
        node.setId(oper.getOperId());
        node.setDataId(oper.getOperId());
        node.setText(oper.getOperName());
        node.setParentId(oper.getMenuId());
        node.getAttributes().put("type", "1");
        node.getAttributes().put("id", oper.getOperId());
        return node;
    }

    public List<SampleMenu> getRootMenus() {
        return rootMenus;
    }

    public void setRootMenus(List<SampleMenu> rootMenus) {
        this.rootMenus = rootMenus;
    }

    public List<SampleMenu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<SampleMenu> childMenus) {
        this.childMenus = childMenus;
    }

    public List<SampleMenuOperation> getChildOpers() {
        return childOpers;
    }

    public void setChildOpers(List<SampleMenuOperation> childOpers) {
        this.childOpers = childOpers;
    }

}
