package com.mycompany.mavenproject1;

public class IntermediateCodeGenerator extends javax.swing.JFrame 
{
    public static int VariableNumber = 1;
    /**
     * Creates new form Intermediate
     * @param text
     */
    public IntermediateCodeGenerator(String text) {
        initComponents();
        String newCode = ConvertCode(text);
        newCode = performFormat(newCode);
        this.jTextArea1.setText(newCode);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setTitle("Intermediate Code Generator");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("3 Address Code");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(190, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Utility Functions
    public static String ConvertCode(String text){
        VariableNumber = 1;
        String finalCode = "";
        String[] lines = text.split("\n");
        for (String line : lines){
            if (isExpressionLine(line)){
                String UpdatedLine = ThreeAddressCode.GenerateCode(line);
                finalCode += UpdatedLine + "\n";
            }
            else {
                finalCode += line + "\n";
            }
        }
        return finalCode;
    }
    
    private static boolean isExpressionLine(String text){ 
        if(text.contains("while") || text.contains("for") || text.contains("do") || 
                text.contains("if") || text.contains("else") || text.contains("private") ||
                text.contains("public") || text.contains("protected") || text.contains("abstract") ||
                text.contains("class") || text.contains("break") || text.contains("continue") ||
                text.contains("try") || text.contains("catch") || text.contains("finally") || 
                text.contains("case") || text.contains("default") || text.contains("enum") || 
                text.contains("extends") || text.contains("implements") || text.contains("interface") ||
                text.contains("package") || text.contains("return") || text.contains("synchronized") || text.equals("throw")) 
        {
            return false;
        }
        else if (text.contains("=") && (text.contains("+") || text.contains("-") || text.contains("/") || text.contains("*") || text.contains("%"))){
            return true;
        }
        else {
            return false;
        }
    }
    public static String performFormat(String text){
        String lines[] = text.split("\n");
        String spaces = "";
        for(int i=0; i<lines.length; i++){
            lines[i] = lines[i].trim();
            if(lines[i].contains("{") && lines[i].contains("}")){
                lines[i] = spaces + lines[i];
            }
            else if(lines[i].contains("{")){
                lines[i] = spaces + lines[i];
                spaces += "    ";
            }
            else if(lines[i].contains("}")){
                if(spaces.length() > 5){
                    spaces = spaces.substring(0, spaces.length()-5);
                }
                else{
                    spaces = "";
                }
                lines[i] = spaces + lines[i];
            }
            else{
                lines[i] = spaces + lines[i];
            }
        }
        String newString = "";
        for(String s : lines){
            newString += s + " \n";
        }
        return newString;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
