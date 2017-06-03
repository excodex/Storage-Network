package mrriegel.storagenetwork;
import mrriegel.storagenetwork.proxy.CommonProxy;
import mrriegel.storagenetwork.request.ContainerRequest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = StorageNetwork.MODID, name = StorageNetwork.MODNAME, version = StorageNetwork.VERSION
, updateJSON = "https://raw.githubusercontent.com/PrinceOfAmber/Storage-Network/master/update.json")
public class StorageNetwork {
  public static final String MODID = "storagenetwork";
  public static final String VERSION = "1.0.0";
  public static final String MODNAME = "Simple Storage Network";
  @Instance(StorageNetwork.MODID)
  public static StorageNetwork instance;
  @SidedProxy(clientSide = "mrriegel.storagenetwork.proxy.ClientProxy", serverSide = "mrriegel.storagenetwork.proxy.CommonProxy")
  public static CommonProxy proxy;
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit(event);
  }
  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
    NBTTagCompound tagCompound = new NBTTagCompound();
    tagCompound.setString("ContainerClass", ContainerRequest.class.getName());
    tagCompound.setBoolean("PhantomItems", false);
    tagCompound.setString("AlignToGrid", "left");
    FMLInterModComms.sendMessage("craftingtweaks", "RegisterProvider", tagCompound);
    MinecraftForge.EVENT_BUS.register(this);
  }
  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
