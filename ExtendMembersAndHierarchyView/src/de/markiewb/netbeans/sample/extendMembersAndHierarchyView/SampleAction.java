package de.markiewb.netbeans.sample.extendMembersAndHierarchyView;

import java.util.ArrayList;
import java.util.List;
import static javax.swing.Action.NAME;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.netbeans.api.java.source.TreePathHandle;
import org.openide.awt.*;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.util.actions.Presenter;

@ActionID(category = "Edit",
	id = "de.markiewb.netbeans.sample.extendMembersAndHierarchyView.SampleAction")
@ActionRegistration(
	displayName = "SampleAction")
@ActionReferences({
    @ActionReference(path = "Navigator/Actions/Members/text/x-java", position = 1150),
    @ActionReference(path = "Navigator/Actions/Hierarchy/text/x-java", position = 1150)
})
/**
 * Shows the usage of the extensionpoints of the context menu in the members and
 * hierarchy views. This is new to NetBeans 7.3. See
 * http://netbeans.org/bugzilla/show_bug.cgi?id=220057 and
 * http://netbeans.org/bugzilla/show_bug.cgi?id=224499.
 *
 * @author markiewb@netbeans.org
 */
public final class SampleAction extends CookieAction implements Presenter.Popup {

    public SampleAction() {
	putValue(NAME, "Hello TreePathHandle(s)");
    }

    @Override
    public String getName() {
	return "Hello TreePathHandle(s)";
    }

    @Override
    public JMenuItem getPopupPresenter() {
	return new JMenuItem(this);
    }

    @Override
    public HelpCtx getHelpCtx() {
	return null;
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
	//.. use tph from lookup in node
	for (Node node : activatedNodes) {
	    if (null != node.getLookup().lookup(TreePathHandle.class)) {
		return true;
	    };
	}
	return false;
    }

    @Override
    protected int mode() {
	return CookieAction.MODE_ALL;
    }

    @Override
    protected Class[] cookieClasses() {
	return new Class[]{Node.class};
    }

    @Override
    protected void performAction(Node[] nodes) {

	List<TreePathHandle> treePathHandles = new ArrayList<TreePathHandle>();
	for (Node node : nodes) {
	    treePathHandles.add(node.getLookup().lookup(TreePathHandle.class));
	}

	//show all treePathHandles
	JOptionPane.showMessageDialog(null, "Hello\n" + treePathHandles);
    }
}
