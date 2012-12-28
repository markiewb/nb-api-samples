/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.sample.contextmenu;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

@ActionID(
        category = "Build",
        id = "de.markiewb.netbeans.sample.contextmenu.HelloIconAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 0),
    @ActionReference(path = "Loaders/Languages/Actions", position = 0),
    @ActionReference(path="Projects/Actions")
})
@ActionRegistration(
        displayName = "#CTL_HelloIconAction")

@Messages("CTL_HelloIconAction=Hello Icon Action")
public final class HelloIconAction extends AbstractAction implements Presenter.Popup {

    @StaticResource
    private static final String ICON = "de/markiewb/netbeans/sample/contextmenu/sample.gif";
    private static final long serialVersionUID = 1L;
    private final Lookup context;

    public HelloIconAction() {
        context = Utilities.actionsGlobalContext();
        putValue(SMALL_ICON, ImageUtilities.loadImageIcon(ICON, false));
        putValue(NAME, Bundle.CTL_HelloIconAction());
    }

    @Override
    public boolean isEnabled() {
        return null != context.lookup(Project.class);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, "Hello colorful project.\n" + context.lookup(Project.class).toString());
    }

    @Override
    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }
}
