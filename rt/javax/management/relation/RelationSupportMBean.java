package javax.management.relation;

public interface RelationSupportMBean extends Relation {
  Boolean isInRelationService();
  
  void setRelationServiceManagementFlag(Boolean paramBoolean) throws IllegalArgumentException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\relation\RelationSupportMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */