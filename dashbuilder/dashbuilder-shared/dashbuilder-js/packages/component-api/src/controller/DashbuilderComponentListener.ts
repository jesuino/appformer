/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { DataSet } from "../dataset";
import { ComponentMessage, MessageType } from "../message";
import { MessageProperty } from "../message/MessageProperty";

import { DashbuilderComponentController } from "./DashbuilderComponentController";
import { InternalComponentListener } from "./InternalComponentListener";

export class DashbuilderComponentListener implements InternalComponentListener {
  public readonly componentController: DashbuilderComponentController;

  private componentId: string;

  constructor(componentController: DashbuilderComponentController) {
    this.componentController = componentController;
    this.componentController.setInternalComponentListener(this);
  }

  private readonly messageDispatcher = (e: MessageEvent) => {
    const message = e.data as ComponentMessage;
    if (message.type === MessageType.INIT) {
      this.componentId = message.properties.get(MessageProperty.COMPONENT_ID);
      this.componentController.onInit(message.properties);
    }

    if (message.type === MessageType.DATASET) {
      const dataSet = message.properties.get(MessageProperty.DATASET) as DataSet;
      this.componentController.onDataSet(dataSet, message.properties);
    }
  };

  public isAutoReady(): boolean {
    // READY not implemented at the moment
    return true;
  }

  public start(): void {
    window.addEventListener("message", this.messageDispatcher, false);
  }

  public sendMessage(componentMessage: ComponentMessage): void {
    componentMessage.properties.set(MessageProperty.COMPONENT_ID, this.componentId);
    window.parent.postMessage(componentMessage, window.location.href);
  }

  public stop(): void {
    window.removeEventListener("message", this.messageDispatcher, false);
  }
}