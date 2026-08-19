package cn.xiaozhou233.juiceremapper.mappings;

import cn.xiaozhou233.juiceremapper.mappings.beans.ClassBean;
import cn.xiaozhou233.juiceremapper.mappings.beans.FieldBean;
import cn.xiaozhou233.juiceremapper.mappings.beans.MethodBean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapping table (SRG -> MCP).
 * Key conventions:
 * - class : full name (e.g. "a")
 * - method: owner/name + " " + descriptor (e.g. "a/a (I)La;")
 * - field : owner/name (e.g. "a/A")
 */
public class MappingTable {

    private final Map<String, ClassBean> classes = new HashMap<>();
    private final Map<String, ClassBean> classesReverse = new HashMap<>();

    private final Map<String, MethodBean> methods = new HashMap<>();
    private final Map<String, MethodBean> methodsReverse = new HashMap<>();

    private final Map<String, FieldBean> fields = new HashMap<>();
    private final Map<String, FieldBean> fieldsReverse = new HashMap<>();

    // =========================
    // Register
    // =========================

    public void addClass(ClassBean bean) {
        classes.put(bean.getObfuscatedName(), bean);
        classesReverse.put(bean.getMcpName(), bean);
    }

    public void addMethod(MethodBean bean) {
        methods.put(bean.getObfuscatedOwner() + "/" + bean.getObfuscatedName() + " " + bean.getObfuscatedDesc(), bean);
        methodsReverse.put(bean.getMcpOwner() + "/" + bean.getMcpName() + " " + bean.getMcpDesc(), bean);
    }

    public void addField(FieldBean bean) {
        fields.put(bean.getObfuscatedFullName(), bean);
        fieldsReverse.put(bean.getMcpFullName(), bean);
    }

    // =========================
    // Lookup (raw beans)
    // =========================

    public ClassBean getClassByObf(String obf) {
        return classes.get(obf);
    }

    public ClassBean getClassByMcp(String mcp) {
        return classesReverse.get(mcp);
    }

    public MethodBean getMethodByObf(String owner, String name, String desc) {
        return methods.get(owner + "/" + name + " " + desc);
    }

    public MethodBean getMethodByMcp(String owner, String name, String desc) {
        return methodsReverse.get(owner + "/" + name + " " + desc);
    }

    public FieldBean getFieldByObf(String owner, String name) {
        return fields.get(owner + "/" + name);
    }

    public FieldBean getFieldByMcp(String owner, String name) {
        return fieldsReverse.get(owner + "/" + name);
    }

    // =========================
    // Convenience (String -> String)
    // =========================

    public String mapClass(String obf) {
        ClassBean bean = classes.get(obf);
        return bean != null ? bean.getMcpName() : obf;
    }

    public String unmapClass(String mcp) {
        ClassBean bean = classesReverse.get(mcp);
        return bean != null ? bean.getObfuscatedName() : mcp;
    }

    public String mapMethod(String owner, String name, String desc) {
        MethodBean bean = methods.get(owner + "/" + name + " " + desc);
        return bean != null ? bean.getMcpName() : null;
    }

    public String unmapMethod(String owner, String name, String desc) {
        MethodBean bean = methodsReverse.get(owner + "/" + name + " " + desc);
        return bean != null ? bean.getObfuscatedName() : null;
    }

    public String mapField(String owner, String name) {
        FieldBean bean = fields.get(owner + "/" + name);
        return bean != null ? bean.getMcpName() : null;
    }

    public String unmapField(String owner, String name) {
        FieldBean bean = fieldsReverse.get(owner + "/" + name);
        return bean != null ? bean.getObfuscatedName() : null;
    }

    // =========================
    // Views
    // =========================

    public Collection<ClassBean> getClasses() {
        return classes.values();
    }

    public Collection<MethodBean> getMethods() {
        return methods.values();
    }

    public Collection<FieldBean> getFields() {
        return fields.values();
    }

    public int classCount() {
        return classes.size();
    }

    public int methodCount() {
        return methods.size();
    }

    public int fieldCount() {
        return fields.size();
    }
}
