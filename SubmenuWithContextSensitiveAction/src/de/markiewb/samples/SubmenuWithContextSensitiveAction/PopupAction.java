package de.markiewb.samples.SubmenuWithContextSensitiveAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import org.netbeans.api.project.Project;
import org.openide.util.Lookup;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.WeakListeners;
import org.openide.util.actions.Presenter;

@ActionID(
        category = "MyActions",
        id = "de.markiewb.netbeans.sample.PopupAction"
)
@ActionRegistration(
        displayName = "#CTL_PopupAction", lazy = false
)
@ActionReferences({
    @ActionReference(path = "Projects/Actions")
})
@Messages("CTL_PopupAction=I am a submenu")
public final class PopupAction extends AbstractAction implements ActionListener, Presenter.Popup {

    private final Lookup.Result<Project> result;
    private final transient LookupListener lookupListener;

    public PopupAction() {
        //create an action, which is only enabled when exactly 2 projects are selected
        result = Utilities.actionsGlobalContext().lookupResult(Project.class);
        this.lookupListener = new LookupListener() {

            @Override
            public void resultChanged(LookupEvent ev) {
                // to make sure that it will be executed on EDT
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        setEnabled(result.allInstances().size() == 2);
                    }
                });
            }
        };
        result.addLookupListener(WeakListeners.create(LookupListener.class, this.lookupListener, result));
        this.lookupListener.resultChanged(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //NOP
    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenu main = new JMenu(Bundle.CTL_PopupAction());
        List<? extends Action> actionsForPath = Utilities.actionsForPath("Actions/MyActions/SubActions");
        for (Action action : actionsForPath) {
            main.add(action);
        }
        return main;
    }
}
