package Utils;

import java.lang.reflect.Field;

public final class ClassHacker
{
  public static void setStaticValue(Field field, Object value)
  {
    try
    {
      Field modifier = Field.class.getDeclaredField("modifiers");

      modifier.setAccessible(true);
      modifier.setInt(field, field.getModifiers() & 0xFFFFFFEF);
      field.set(null, value); } catch (Exception ex) {
    }
  }

  public static void setPrivateValue(Object obj, String name, Object value) {
    try {
      Field field = obj.getClass().getDeclaredField(name);
      field.setAccessible(true);
      field.set(obj, value);
    }
    catch (IllegalArgumentException ex) {
    }
    catch (IllegalAccessException ex) {
    }
    catch (Exception ex) {
    }
  }

  public static Object getPrivateValue(Object obj, String name) {
    try {
      Field field = obj.getClass().getDeclaredField(name);
      field.setAccessible(true);
      return field.get(obj); } catch (Exception ex) {
    }
    return null;
  }
}