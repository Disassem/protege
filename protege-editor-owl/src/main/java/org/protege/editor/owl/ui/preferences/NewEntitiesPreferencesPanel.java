package org.protege.editor.owl.ui.preferences;

import org.protege.editor.owl.model.entity.*;
import org.protege.editor.owl.ui.UIHelper;
import org.protege.editor.owl.ui.renderer.OWLRendererPreferences;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>

 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Jul 24, 2008<br><br>
 *
 * see http://protegewiki.stanford.edu/index.php/Protege4NamingAndRendering
 */
public class NewEntitiesPreferencesPanel extends OWLPreferencesPanel implements ActionListener {

	private static final long serialVersionUID = -319986509127880607L;
    private static final String SEP_COLON = ":";
    private static final String SEP_HASH = "#";
    private static final String SEP_SLASH = "/";

	private final Logger logger = LoggerFactory.getLogger(NewEntitiesPreferencesPanel.class);
    
    // Entity IRI panel
    private JRadioButton autoIDIriFragment;
    private JRadioButton colonButton;
    private JRadioButton hashButton;
    private JRadioButton nameAsIriFragment;
    private JRadioButton slashButton;
    private JRadioButton iriBaseActiveOntology;
    private JRadioButton iriBaseSpecifiedIri;
    private JTextField iriDefaultBaseField;

    // Entity Label panel
    private IRI labelAnnotation = null;
    private JButton annotationSelectButton;
    private JComboBox annotationLangSelector;
	private JLabel langLabel;
    private JLabel iriLabel;
    private JRadioButton customLabelButton;
    private JRadioButton sameAsRendererLabelButton;
    private JTextField annotationIriLabel;

    // Auto-generated ID panel
    private JCheckBox saveIterativeIds;
    private JLabel digitCountLabel;
    private JLabel endLabel;
    private JLabel prefixLabel;
    private JLabel startLabel;
    private JLabel suffixLabel;
    private JPanel autoGeneratedIDPanel;
    private JRadioButton iterativeButton;
    private JRadioButton uniqueIdButton;
    private JSpinner autoIDEnd;
    private JSpinner autoIDStart;
    private JSpinner autoIDDigitCount;
    private JTextField autoIDPrefix;
    private JTextField autoIDSuffix;

    public void initialise() throws Exception {
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	add(createEntityIriPanel());
        add(Box.createVerticalStrut(7));
    	add(createEntityLabelPanel());
        add(Box.createVerticalStrut(7));
    	add(createAutoGeneratedIDPanel());
    }
    
    private JPanel createEntityIriPanel() {
    	JPanel panel = new JPanel();
    	panel.setBorder(new TitledBorder("Entity IRI"));
    	panel.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	
    	// "Start with:" section
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 1;
    	c.gridheight = 1;
    	c.fill = GridBagConstraints.NONE;
    	c.insets = new Insets(12,12,0,12);
    	c.anchor = GridBagConstraints.FIRST_LINE_START;
    	panel.add(new JLabel("Start with:"), c);
    	
    	c.gridx = 1;
    	c.gridy = 0;
    	c.gridwidth = 2;
    	c.insets = new Insets(12,0,0,0);
    	iriBaseActiveOntology = new JRadioButton("Active ontology IRI");
    	iriBaseActiveOntology.setSelected(!EntityCreationPreferences.useDefaultBaseIRI());
    	panel.add(iriBaseActiveOntology, c);
    	
    	c.gridx = 1;
    	c.gridy = 1;
    	c.gridwidth = 1;
    	c.insets = new Insets(0,0,0,5);
    	iriBaseSpecifiedIri = new JRadioButton("Specified IRI:");
    	iriBaseSpecifiedIri.setSelected(EntityCreationPreferences.useDefaultBaseIRI());
    	panel.add(iriBaseSpecifiedIri, c);
    	
    	c.gridx = 2;
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 1.0;
    	c.insets = new Insets(0,0,0,12);
    	iriDefaultBaseField = new JTextField();
        iriDefaultBaseField.setText(EntityCreationPreferences.getDefaultBaseIRI().toString());
    	panel.add(iriDefaultBaseField, c);
    	
        ButtonGroup group = new ButtonGroup();
        group.add(iriBaseActiveOntology);
        group.add(iriBaseSpecifiedIri);
        
        // "Followed by:" section
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(11,12,0,12);
        c.weightx = 0;
        panel.add(new JLabel("Followed by:"), c);
        
        c.gridx = 1;
        c.insets = new Insets(11,0,0,0);
        hashButton = new JRadioButton(SEP_HASH);
        hashButton.setSelected(EntityCreationPreferences.getDefaultSeparator().equals(SEP_HASH));
        panel.add(hashButton, c);

        c.gridy = 3;
        c.insets = new Insets(0,0,0,0);
        slashButton = new JRadioButton(SEP_SLASH);
        slashButton.setSelected(EntityCreationPreferences.getDefaultSeparator().equals(SEP_SLASH));
        panel.add(slashButton, c);
        
        c.gridy = 4;
        c.insets = new Insets(0,0,0,0);
        colonButton = new JRadioButton(SEP_COLON);
        colonButton.setSelected(EntityCreationPreferences.getDefaultSeparator().equals(SEP_COLON));
        panel.add(colonButton, c);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(hashButton);
        group2.add(slashButton);
        group2.add(colonButton);
        
        // "End with:" section
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(11,12,0,12);
        panel.add(new JLabel("End with:"), c);
        
        c.gridx = 1;
        c.insets = new Insets(11,0,0,0);
    	c.gridwidth = 2;
        nameAsIriFragment = new JRadioButton("User supplied name");
        nameAsIriFragment.setSelected(!EntityCreationPreferences.isFragmentAutoGenerated());
        nameAsIriFragment.addActionListener(e -> {
            handleActionEndWithName();
        });
        panel.add(nameAsIriFragment, c);
        
        c.gridy = 6;
        c.insets = new Insets(0,0,12,0);
        c.weighty = 1.0;
        autoIDIriFragment = new JRadioButton("Auto-generated ID");
        autoIDIriFragment.setSelected(EntityCreationPreferences.isFragmentAutoGenerated());
        autoIDIriFragment.addActionListener(e -> {
            handleActionEndWithID();
        });
        panel.add(autoIDIriFragment, c);
        
        ButtonGroup group3 = new ButtonGroup();
        group3.add(nameAsIriFragment);
        group3.add(autoIDIriFragment);
        
    	return panel;
    }
    
	private JPanel createEntityLabelPanel() {
        final Class<? extends LabelDescriptor> labelDescrCls = EntityCreationPreferences.getLabelDescriptorClass();

    	JPanel panel = new JPanel();
    	panel.setBorder(new TitledBorder("Entity Label (for use with Auto-generated ID)"));
    	panel.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 2;
    	c.gridheight = 1;
    	c.fill = GridBagConstraints.NONE;
    	c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0,12,0,0);
		/*sameAsRendererLabelButton = new JRadioButton("Same as label renderer (currently " 
				+ new SimpleIRIShortFormProvider().getShortForm(getFirstRendererLabel()) + ")");*/
		sameAsRendererLabelButton = new JRadioButton("Same as label renderer");
        sameAsRendererLabelButton.setSelected(labelDescrCls.equals(MatchRendererLabelDescriptor.class));
        panel.add(sameAsRendererLabelButton, c);
        
        c.gridy = 1;        
		customLabelButton = new JRadioButton("Custom label");
		customLabelButton.setSelected(labelDescrCls.equals(CustomLabelDescriptor.class));
		panel.add(customLabelButton, c);
		
		ButtonGroup group = new ButtonGroup();
		group.add(sameAsRendererLabelButton);
		group.add(customLabelButton);
		
		c.gridy = 2;
		c.gridwidth = 1;
		c.insets = new Insets(5,30,0,5);
		iriLabel = new JLabel("IRI");
		panel.add(iriLabel, c);
		
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,0,0,0);
		c.weightx = 1.0;
		annotationIriLabel = new JTextField();
        labelAnnotation = EntityCreationPreferences.getNameLabelIRI();
        if (labelAnnotation == null){
            labelAnnotation = OWLRDFVocabulary.RDFS_LABEL.getIRI();
        }
        annotationIriLabel.setText(labelAnnotation.toString());
		annotationIriLabel.setEditable(false);
		panel.add(annotationIriLabel, c);
    	
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(5,5,0,12);
        c.weightx = 0;
		annotationSelectButton = new JButton(new AbstractAction("...") {
			private static final long serialVersionUID = 7759812643136092837L;

			public void actionPerformed(ActionEvent event) {
                handleSelectAnnotation();
            }
        });
		panel.add(annotationSelectButton, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(5,30,12,5);
		langLabel = new JLabel("Lang");
		panel.add(langLabel, c);		
		
		c.gridx = 1;
		c.insets = new Insets(5,0,12,0);
        c.weighty = 1.0;
		annotationLangSelector = new UIHelper(getOWLEditorKit()).getLanguageSelector();
        annotationLangSelector.setSelectedItem(EntityCreationPreferences.getNameLabelLang());
		panel.add(annotationLangSelector, c);
		
		return panel;
    }
	
	private JPanel createAutoGeneratedIDPanel() {
		autoGeneratedIDPanel = new JPanel();
		autoGeneratedIDPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
    	autoGeneratedIDPanel.setBorder(new TitledBorder("Auto-generated ID"));
    	
    	JPanel interiorPanel = new JPanel(new BorderLayout(32, 0));
    	interiorPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 0, 0));
    	
        // Left panel - radio buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));

        iterativeButton = new JRadioButton("Numeric (iterative)");
        uniqueIdButton = new JRadioButton("Globally unique");
        
        final Class<? extends AutoIDGenerator> autoIDGenCls = EntityCreationPreferences.getAutoIDGeneratorClass();
        iterativeButton.setSelected(autoIDGenCls.equals(IterativeAutoIDGenerator.class));
        uniqueIdButton.setSelected(autoIDGenCls.equals(UniqueIdGenerator.class));
        
        ButtonGroup group = new ButtonGroup();
        group.add(iterativeButton);
        group.add(uniqueIdButton);
        
        iterativeButton.addActionListener(this);
        uniqueIdButton.addActionListener(this);
        
        leftPanel.add(iterativeButton);
        leftPanel.add(uniqueIdButton);
        leftPanel.add(Box.createVerticalGlue());
        
    	// Center panel - random group of components
        JPanel centerPanel = new JPanel();
    	centerPanel.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
        
        // Prefix label
    	c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,0,5,0);
        c.anchor = GridBagConstraints.LINE_END;
        prefixLabel = new JLabel("Prefix: "); 
        centerPanel.add(prefixLabel, c);
    	
    	// Prefix text field
        c.gridx = 1;
    	c.gridy = 0;
    	c.anchor = GridBagConstraints.FIRST_LINE_START;
        autoIDPrefix = new JTextField();
        autoIDPrefix.setText(EntityCreationPreferences.getPrefix());
        autoIDPrefix.setColumns(30);
        centerPanel.add(autoIDPrefix, c);
        
        // Suffix label
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_END;
        suffixLabel = new JLabel("Suffix: ");
        centerPanel.add(suffixLabel, c);
    	
    	// Suffix text field
        c.gridx = 1;
    	c.gridy = 1;
    	c.anchor = GridBagConstraints.FIRST_LINE_START;
    	autoIDSuffix = new JTextField();
    	autoIDSuffix.setText(EntityCreationPreferences.getSuffix());
    	autoIDSuffix.setColumns(30);
    	centerPanel.add(autoIDSuffix, c);
    	
        // Digit count label
    	c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_END;
        digitCountLabel = new JLabel("Digit count: ");
        centerPanel.add(digitCountLabel, c);
        
        // Digit count spinner
        c.gridx = 1;
        c.gridy = 2;
    	c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 0.5;
        autoIDDigitCount = new JSpinner(new SpinnerNumberModel(6, 0, 255, 1));
        autoIDDigitCount.setValue(EntityCreationPreferences.getAutoIDDigitCount());
        autoIDDigitCount.setPreferredSize(new Dimension(100, 20));
        centerPanel.add(autoIDDigitCount, c);
        
        // Start label
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.anchor = GridBagConstraints.LINE_END;
        startLabel = new JLabel("Start: ");
        startLabel.setEnabled(iterativeButton.isSelected());
        centerPanel.add(startLabel, c);
        
        // Start spinner
        c.gridx = 1;
        c.gridy = 3;
    	c.anchor = GridBagConstraints.FIRST_LINE_START;
    	autoIDStart = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        autoIDStart.setPreferredSize(new Dimension(100, 20));
        autoIDStart.setValue(EntityCreationPreferences.getAutoIDStart());
        autoIDStart.setEnabled(iterativeButton.isSelected());
        autoIDStart.addChangeListener(event -> {
            if ((Integer)autoIDEnd.getValue() != -1 && (Integer)autoIDEnd.getValue() <= (Integer)autoIDStart.getValue()) {
                autoIDEnd.setValue(autoIDStart.getValue());
            }
        });
        centerPanel.add(autoIDStart, c);
        
        // End label
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_END;
        endLabel = new JLabel("End: ");
        endLabel.setEnabled(iterativeButton.isSelected());
        centerPanel.add(endLabel, c);
        
        // End spinner
        c.gridx = 1;
        c.gridy = 4;
    	c.anchor = GridBagConstraints.FIRST_LINE_START;
        autoIDEnd = new JSpinner(new SpinnerNumberModel(-1, -1, Integer.MAX_VALUE, 1));
        autoIDEnd.setPreferredSize(new Dimension(100, 20));
        autoIDEnd.setValue(EntityCreationPreferences.getAutoIDEnd());
        autoIDEnd.setEnabled(iterativeButton.isSelected());
        autoIDEnd.addChangeListener(event -> {
            if ((Integer)autoIDEnd.getValue() != -1 && (Integer)autoIDEnd.getValue() <= (Integer)autoIDStart.getValue()) {
                autoIDStart.setValue(autoIDEnd.getValue());
            }
        });
        centerPanel.add(autoIDEnd, c);
        
        // Remember last ID checkbox
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1.0;
        saveIterativeIds = new JCheckBox("Remember last ID between Prot\u00E9g\u00E9 sessions");
        saveIterativeIds.setSelected(EntityCreationPreferences.getSaveAutoIDStart());
        saveIterativeIds.setEnabled(iterativeButton.isSelected());
        centerPanel.add(saveIterativeIds, c);
        
        // Dummy label for spacing purposes
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 0;
        centerPanel.add(new JLabel(""));

        enableAutoGeneratedIDPanel(autoIDIriFragment.isSelected());
        enableLabelCreationPanel(autoIDIriFragment.isSelected());
        
        interiorPanel.add(leftPanel, BorderLayout.LINE_START);
        interiorPanel.add(centerPanel, BorderLayout.CENTER);
        autoGeneratedIDPanel.add(interiorPanel);
    	return autoGeneratedIDPanel;
	}
	
	private void enableAutoGeneratedIDPanel(boolean b) {
	    autoIDDigitCount.setEnabled(b);
	    autoIDEnd.setEnabled(b);
	    autoIDPrefix.setEnabled(b);
	    autoIDStart.setEnabled(b);
	    autoIDSuffix.setEnabled(b);
	    autoGeneratedIDPanel.setEnabled(b);
	    digitCountLabel.setEnabled(b);
	    endLabel.setEnabled(b);
		iterativeButton.setEnabled(b);
		prefixLabel.setEnabled(b);
	    saveIterativeIds.setEnabled(b);
	    startLabel.setEnabled(b);
	    suffixLabel.setEnabled(b);
		uniqueIdButton.setEnabled(b);
		
		enableNumericIterativeOptions((iterativeButton.isSelected()) && (iterativeButton.isEnabled()));
	}
	
	private void enableLabelCreationPanel(boolean b) {
		annotationSelectButton.setEnabled(b);
		annotationLangSelector.setEnabled(b);
		customLabelButton.setEnabled(b);
		sameAsRendererLabelButton.setEnabled(b);
		annotationIriLabel.setEnabled(b);
	}
	
	private void enableNumericIterativeOptions(boolean b) {
		startLabel.setEnabled(b);
		autoIDStart.setEnabled(b);
		endLabel.setEnabled(b);
		autoIDEnd.setEnabled(b);
		saveIterativeIds.setEnabled(b);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		
		if (object == iterativeButton) { // "Numeric (iterative)"
			enableNumericIterativeOptions(true);
		} else if (object == uniqueIdButton) { // "Numeric (pseudo random)", "Unique and meaningless" 
			enableNumericIterativeOptions(false);
		}
	}
	
    public void applyChanges() {
        EntityCreationPreferences.setUseDefaultBaseIRI(iriBaseSpecifiedIri.isSelected());
        try {
            IRI defaultBase = IRI.create(new URI(iriDefaultBaseField.getText()));
            EntityCreationPreferences.setDefaultBaseIRI(defaultBase);
        }
        catch (URISyntaxException e) {
            logger.error("Ignoring invalid base IRI ({})", iriDefaultBaseField.getText(), e);
        }

        if (hashButton.isSelected()){
            EntityCreationPreferences.setDefaultSeparator(SEP_HASH);
        }
        else if (slashButton.isSelected()){
            EntityCreationPreferences.setDefaultSeparator(SEP_SLASH);
        }
        else if (colonButton.isSelected()){
            EntityCreationPreferences.setDefaultSeparator(SEP_COLON);
        }

        EntityCreationPreferences.setFragmentAutoGenerated(autoIDIriFragment.isSelected());

        EntityCreationPreferences.setGenerateNameLabel(autoIDIriFragment.isSelected());
        EntityCreationPreferences.setGenerateIDLabel(false);

        if (sameAsRendererLabelButton.isSelected()){
            EntityCreationPreferences.setLabelDescriptorClass(MatchRendererLabelDescriptor.class);
        }
        if (customLabelButton.isSelected()){
            EntityCreationPreferences.setLabelDescriptorClass(CustomLabelDescriptor.class);
        }

        EntityCreationPreferences.setNameLabelIRI(IRI.create(annotationIriLabel.getText()));
        Object lang = annotationLangSelector.getSelectedItem();
        if (lang != null && !lang.equals("")){
            EntityCreationPreferences.setNameLabelLang((String)lang);
        }
        else{
            EntityCreationPreferences.setNameLabelLang(null);
        }

        if (iterativeButton.isSelected()){
            EntityCreationPreferences.setAutoIDGeneratorClass(IterativeAutoIDGenerator.class);
        }
        if (uniqueIdButton.isSelected()) {
        	EntityCreationPreferences.setAutoIDGeneratorClass(UniqueIdGenerator.class);
        }

        EntityCreationPreferences.setAutoIDStart((Integer)autoIDStart.getValue());
        EntityCreationPreferences.setAutoIDEnd((Integer)autoIDEnd.getValue());

        EntityCreationPreferences.setAutoIDDigitCount((Integer)autoIDDigitCount.getValue());
        EntityCreationPreferences.setPrefix(autoIDPrefix.getText());
        EntityCreationPreferences.setSuffix(autoIDSuffix.getText());
        EntityCreationPreferences.setSaveAutoIDStart(saveIterativeIds.isSelected());
    }
    
    private void handleActionEndWithID() {
    	boolean selected = autoIDIriFragment.isSelected();
    	enableAutoGeneratedIDPanel(selected);
    	enableLabelCreationPanel(selected);
    }
    
    private void handleActionEndWithName() {
    	boolean selected = nameAsIriFragment.isSelected();
    	if (selected) {
    		enableAutoGeneratedIDPanel(false);
        	enableLabelCreationPanel(false);
    	}
    }

    protected void handleSelectAnnotation() {
        OWLAnnotationProperty prop = new UIHelper(getOWLEditorKit()).pickAnnotationProperty();
        if (prop != null){
            labelAnnotation = prop.getIRI();
            annotationIriLabel.setText(labelAnnotation.toString());
        }
    }
    
    public IRI getFirstRendererLabel() {
        final java.util.List<IRI> iris = OWLRendererPreferences.getInstance().getAnnotationIRIs();
        if (!iris.isEmpty()){
            return iris.get(0);
        }
        return OWLRDFVocabulary.RDFS_LABEL.getIRI();
    }

    public void dispose() throws Exception {
    }
}
