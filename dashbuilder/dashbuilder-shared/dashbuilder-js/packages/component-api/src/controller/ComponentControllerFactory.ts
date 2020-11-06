import { DataSet } from "../dataset";
import { ComponentController } from "./ComponentController";
import { DashbuilderComponentController } from "./DashbuilderComponentController";
import { DashbuilderComponentListener } from "./DashbuilderComponentListener";

class ComponentControllerFactory {
  private controller: DashbuilderComponentController = new DashbuilderComponentController();
  private listener: DashbuilderComponentListener = new DashbuilderComponentListener(this.controller);

  public start() {
    this.listener.start();
  }

  public getComponentController(
    onInit?: (params: Map<string, any>) => void,
    onDataSet?: (dataSet: DataSet, params?: Map<string, any>) => void
  ): ComponentController {
    if (onInit) {
      this.controller.setOnInit(onInit);
    }
    if (onDataSet) {
      this.controller.setOnDataSet(onDataSet);
    }
    return this.controller;
  }

  public destroy() {
    this.listener.stop();
  }
}
const instance = new ComponentControllerFactory();
instance.start();

export const get = () => instance;